package com.davendra.event_booking.modules.booking.scheduler;

import com.davendra.event_booking.modules.booking.enums.BookingStatus;
import com.davendra.event_booking.modules.booking.repo.BookingRepository;
import com.davendra.event_booking.modules.booking.service.BookingService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;

@Slf4j
@Component
@RequiredArgsConstructor
public class BookingExpiryScheduler {

    private final BookingRepository bookingRepository;
    private final BookingService bookingService;

    @Scheduled(fixedDelayString = "${booking.expiry.scheduler.delay-ms:60000}")
    public void expireReservations() {
        List<Long> expiredBookingIds = bookingRepository.findExpiredBookingIds(
                BookingStatus.PENDING,
                LocalDateTime.now()
        );

        for (Long bookingId : expiredBookingIds) {
            try {
                bookingService.expireBooking(bookingId);
            } catch (Exception ex) {
                log.warn("Failed to expire booking id={}", bookingId, ex);
            }
        }
    }
}
