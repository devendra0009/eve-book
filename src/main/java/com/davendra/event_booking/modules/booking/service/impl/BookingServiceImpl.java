package com.davendra.event_booking.modules.booking.service.impl;

import com.davendra.event_booking.common.exception.BadRequestException;
import com.davendra.event_booking.common.exception.ResourceNotFoundException;
import com.davendra.event_booking.common.exception.SeatReservationConflictException;
import com.davendra.event_booking.modules.booking.cache.SeatReservationCacheFacade;
import com.davendra.event_booking.modules.booking.dtos.request.BookingReservationRequest;
import com.davendra.event_booking.modules.booking.dtos.response.BookingReservationResponse;
import com.davendra.event_booking.modules.booking.entity.BookingEntity;
import com.davendra.event_booking.modules.booking.entity.BookingSeatEntity;
import com.davendra.event_booking.modules.booking.enums.BookingSeatStatus;
import com.davendra.event_booking.modules.booking.enums.BookingStatus;
import com.davendra.event_booking.modules.booking.enums.PaymentStatus;
import com.davendra.event_booking.modules.booking.mapper.BookingMapper;
import com.davendra.event_booking.modules.booking.repo.BookingRepository;
import com.davendra.event_booking.modules.booking.service.BookingService;
import com.davendra.event_booking.modules.event.entity.ShowEntity;
import com.davendra.event_booking.modules.event.repo.ShowRepository;
import com.davendra.event_booking.modules.inventory.entity.ShowSeatEntity;
import com.davendra.event_booking.modules.inventory.enums.ShowSeatStatus;
import com.davendra.event_booking.modules.inventory.repo.ShowSeatRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.OptimisticLockingFailureException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

@Service
@RequiredArgsConstructor
public class BookingServiceImpl implements BookingService {

    private static final int RESERVATION_TTL_MINUTES = 5;

    private final BookingRepository bookingRepository;
    private final ShowRepository showRepository;
    private final ShowSeatRepository showSeatRepository;
    private final SeatReservationCacheFacade seatReservationCacheFacade;
    private final BookingMapper bookingMapper;

    @Override
    @Transactional
    public BookingReservationResponse reserveSeats(BookingReservationRequest request, String userId) {
        validateUserId(userId);
        validateReservationRequest(request);

        Long showId = request.getShowId();
        List<Long> seatIds = request.getSeatIds().stream()
                .distinct()
                .sorted(Comparator.naturalOrder())
                .toList();

        for (Long seatId : seatIds) {
            if (seatReservationCacheFacade.isHeldByAnotherUser(showId, seatId, userId)) {
                throw new SeatReservationConflictException(
                        "Seat " + seatId + " is reserved. Try again later."
                );
            }
        }

        ShowEntity show = showRepository.findById(showId)
                .orElseThrow(() -> new ResourceNotFoundException("Show not found with id: " + showId));

        if (Boolean.FALSE.equals(show.getBookingOpen())) {
            throw new BadRequestException("Booking is not open for this show");
        }

        LocalDateTime expiresAt = LocalDateTime.now().plusMinutes(RESERVATION_TTL_MINUTES);
        List<ShowSeatEntity> reservedShowSeats = new ArrayList<>();

        try {
            for (Long seatId : seatIds) {
                ShowSeatEntity lockedSeat = showSeatRepository.findForUpdateByShowIdAndSeatId(showId, seatId)
                        .orElseThrow(() -> new ResourceNotFoundException(
                                "Show seat not found for show " + showId + " and seat " + seatId
                        ));

                if (!lockShowSeat(lockedSeat, userId, expiresAt)) {
                    throw new SeatReservationConflictException(
                            "Seat " + seatId + " is reserved. Try again later."
                    );
                }

                Long showSeatId = lockedSeat.getId();
                ShowSeatEntity refreshedSeat = showSeatRepository.findById(showSeatId)
                        .orElseThrow(() -> new ResourceNotFoundException(
                                "Show seat not found with id: " + showSeatId
                        ));
                reservedShowSeats.add(refreshedSeat);
            }
        } catch (OptimisticLockingFailureException ex) {
            throw new SeatReservationConflictException(
                    "Seat reservation conflict due to concurrent update. Try again later."
            );
        }

        BigDecimal totalAmount = reservedShowSeats.stream()
                .map(ShowSeatEntity::getPrice)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        BookingEntity booking = BookingEntity.builder()
                .userId(userId)
                .show(show)
                .totalAmount(totalAmount)
                .bookingStatus(BookingStatus.PENDING)
                .paymentStatus(PaymentStatus.PENDING)
                .expiresAt(expiresAt)
                .build();

        for (ShowSeatEntity showSeat : reservedShowSeats) {
            BookingSeatEntity bookingSeat = BookingSeatEntity.builder()
                    .booking(booking)
                    .show(show)
                    .seat(showSeat.getSeat())
                    .price(showSeat.getPrice())
                    .amount(showSeat.getPrice())
                    .status(BookingSeatStatus.PENDING)
                    .build();
            booking.getSeats().add(bookingSeat);
        }

        BookingEntity saved = bookingRepository.save(booking);

        for (Long seatId : seatIds) {
            seatReservationCacheFacade.markReserved(showId, seatId, userId);
        }

        return bookingMapper.toReservationResponse(saved);
    }

    @Override
    @Transactional(readOnly = true)
    public BookingReservationResponse getBooking(Long bookingId, String userId) {
        validateUserId(userId);
        BookingEntity booking = loadBookingForUser(bookingId, userId);
        return bookingMapper.toReservationResponse(booking);
    }

    @Override
    @Transactional
    public BookingReservationResponse confirmBooking(Long bookingId, String userId) {
        validateUserId(userId);
        BookingEntity booking = loadBookingForUser(bookingId, userId);
        assertPendingAndNotExpired(booking);

        confirmInventorySeats(booking);
        booking.setBookingStatus(BookingStatus.CONFIRMED);
        booking.setPaymentStatus(PaymentStatus.CONFIRMED);
        booking.getSeats().forEach(seat -> seat.setStatus(BookingSeatStatus.CONFIRMED));

        return bookingMapper.toReservationResponse(bookingRepository.save(booking));
    }

    @Override
    @Transactional
    public BookingReservationResponse cancelBooking(Long bookingId, String userId) {
        validateUserId(userId);
        BookingEntity booking = loadBookingForUser(bookingId, userId);
        assertPendingAndNotExpired(booking);

        releaseInventorySeats(booking);
        booking.setBookingStatus(BookingStatus.CANCELLED);
        booking.getSeats().forEach(seat -> seat.setStatus(BookingSeatStatus.CANCELLED));

        return bookingMapper.toReservationResponse(bookingRepository.save(booking));
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void expireBooking(Long bookingId) {
        BookingEntity booking = bookingRepository.findByIdWithSeats(bookingId)
                .orElseThrow(() -> new ResourceNotFoundException("Booking not found with id: " + bookingId));

        if (booking.getBookingStatus() != BookingStatus.PENDING) {
            return;
        }

        if (booking.getExpiresAt() != null && booking.getExpiresAt().isAfter(LocalDateTime.now())) {
            return;
        }

        releaseInventorySeats(booking);
        booking.setBookingStatus(BookingStatus.EXPIRED);
        booking.setPaymentStatus(PaymentStatus.EXPIRED);
        booking.getSeats().forEach(seat -> seat.setStatus(BookingSeatStatus.EXPIRED));
        bookingRepository.save(booking);
    }

    private void confirmInventorySeats(BookingEntity booking) {
        Long showId = booking.getShow().getId();
        String userId = booking.getUserId();

        for (BookingSeatEntity bookingSeat : booking.getSeats()) {
            Long seatId = bookingSeat.getSeat().getId();
            showSeatRepository.findForUpdateByShowIdAndSeatId(showId, seatId)
                    .orElseThrow(() -> new ResourceNotFoundException(
                            "Show seat not found for show " + showId + " and seat " + seatId
                    ));

            int updated = showSeatRepository.confirmSeatIfReservedByUser(
                    showId,
                    seatId,
                    userId,
                    ShowSeatStatus.BOOKED,
                    ShowSeatStatus.RESERVED
            );

            if (updated != 1) {
                throw new SeatReservationConflictException(
                        "Unable to confirm seat " + seatId + ". Reservation may have expired."
                );
            }

            seatReservationCacheFacade.release(showId, seatId);
        }
    }

    private void releaseInventorySeats(BookingEntity booking) {
        Long showId = booking.getShow().getId();
        String userId = booking.getUserId();
        LocalDateTime releasedAt = LocalDateTime.now();

        for (BookingSeatEntity bookingSeat : booking.getSeats()) {
            Long seatId = bookingSeat.getSeat().getId();
            showSeatRepository.findForUpdateByShowIdAndSeatId(showId, seatId)
                    .ifPresent(ignored -> showSeatRepository.releaseSeatIfReservedByUser(
                            showId,
                            seatId,
                            userId,
                            ShowSeatStatus.AVAILABLE,
                            ShowSeatStatus.RESERVED,
                            releasedAt
                    ));
            seatReservationCacheFacade.release(showId, seatId);
        }
    }

    private BookingEntity loadBookingForUser(Long bookingId, String userId) {
        BookingEntity booking = bookingRepository.findByIdWithSeats(bookingId)
                .orElseThrow(() -> new ResourceNotFoundException("Booking not found with id: " + bookingId));

        if (!userId.equals(booking.getUserId())) {
            throw new BadRequestException("Booking does not belong to the authenticated user");
        }

        return booking;
    }

    private void assertPendingAndNotExpired(BookingEntity booking) {
        if (booking.getBookingStatus() != BookingStatus.PENDING) {
            throw new BadRequestException("Booking is not in a pending state");
        }

        if (booking.getExpiresAt() != null && booking.getExpiresAt().isBefore(LocalDateTime.now())) {
            throw new BadRequestException("Booking reservation has expired");
        }
    }

    private void validateUserId(String userId) {
        if (userId == null || userId.isBlank()) {
            throw new BadRequestException("Authenticated user is required");
        }
    }

    private void validateReservationRequest(BookingReservationRequest request) {
        if (request == null || request.getShowId() == null) {
            throw new BadRequestException("showId is required");
        }
        if (request.getSeatIds() == null || request.getSeatIds().isEmpty()) {
            throw new BadRequestException("At least one seatId is required");
        }
    }

    private boolean lockShowSeat(ShowSeatEntity showSeat, String userId, LocalDateTime lockedUntil) {
        ShowSeatStatus status = showSeat.getStatus();

        if (status == ShowSeatStatus.BOOKED
                || status == ShowSeatStatus.BLOCKED
                || status == ShowSeatStatus.MAINTENANCE) {
            return false;
        }

        if (status == ShowSeatStatus.AVAILABLE) {
            return showSeatRepository.lockSeatIfAvailable(
                    showSeat.getId(),
                    ShowSeatStatus.RESERVED,
                    ShowSeatStatus.AVAILABLE,
                    userId,
                    lockedUntil
            ) == 1;
        }

        if (status == ShowSeatStatus.RESERVED) {
            if (isExpired(showSeat)) {
                return showSeatRepository.reclaimExpiredReservation(
                        showSeat.getId(),
                        ShowSeatStatus.RESERVED,
                        ShowSeatStatus.RESERVED,
                        userId,
                        lockedUntil,
                        LocalDateTime.now()
                ) == 1;
            }
            if (userId.equals(showSeat.getLockedByUser())) {
                showSeat.setLockedUntil(lockedUntil);
                showSeatRepository.save(showSeat);
                return true;
            }
            return false;
        }

        return false;
    }

    private boolean isExpired(ShowSeatEntity showSeat) {
        return showSeat.getLockedUntil() != null
                && showSeat.getLockedUntil().isBefore(LocalDateTime.now());
    }
}
