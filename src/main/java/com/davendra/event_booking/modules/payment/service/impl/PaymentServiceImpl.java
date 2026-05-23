package com.davendra.event_booking.modules.payment.service.impl;

import com.davendra.event_booking.common.exception.BadRequestException;
import com.davendra.event_booking.common.exception.ResourceNotFoundException;
import com.davendra.event_booking.modules.booking.entity.BookingEntity;
import com.davendra.event_booking.modules.booking.enums.BookingStatus;
import com.davendra.event_booking.modules.booking.enums.PaymentStatus;
import com.davendra.event_booking.modules.booking.repo.BookingRepository;
import com.davendra.event_booking.modules.payment.dtos.request.PaymentInitRequest;
import com.davendra.event_booking.modules.payment.dtos.response.PaymentInitResponse;
import com.davendra.event_booking.modules.payment.enums.PaymentMethodType;
import com.davendra.event_booking.modules.payment.model.PaymentContext;
import com.davendra.event_booking.modules.payment.service.PaymentAsyncProcessor;
import com.davendra.event_booking.modules.payment.service.PaymentService;
import com.davendra.event_booking.modules.payment.strategy.PaymentStrategyRegistry;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class PaymentServiceImpl implements PaymentService {

    private final BookingRepository bookingRepository;
    private final PaymentStrategyRegistry paymentStrategyRegistry;
    private final PaymentAsyncProcessor paymentAsyncProcessor;

    @Override
    @Transactional(readOnly = true)
    public PaymentInitResponse initiatePayment(Long bookingId, PaymentInitRequest request, String userId) {
        validateUserId(userId);
        validateRequest(request);

        BookingEntity booking = bookingRepository.findByIdWithSeats(bookingId)
                .orElseThrow(() -> new ResourceNotFoundException("Booking not found with id: " + bookingId));

        if (!userId.equals(booking.getUserId())) {
            throw new BadRequestException("Booking does not belong to the authenticated user");
        }

        if (booking.getBookingStatus() != BookingStatus.PENDING) {
            throw new BadRequestException("Booking is not in a pending state");
        }

        if (booking.getPaymentStatus() != PaymentStatus.PENDING) {
            throw new BadRequestException("Payment has already been initiated or completed");
        }

        if (booking.getExpiresAt() != null && booking.getExpiresAt().isBefore(LocalDateTime.now())) {
            throw new BadRequestException("Booking reservation has expired");
        }

        paymentStrategyRegistry.getStrategy(request.getPaymentMethod());

        PaymentContext context = PaymentContext.builder()
                .bookingId(bookingId)
                .userId(userId)
                .amount(booking.getTotalAmount())
                .paymentMethod(request.getPaymentMethod())
                .build();

        paymentAsyncProcessor.process(context);

        return PaymentInitResponse.builder()
                .bookingId(bookingId)
                .paymentMethod(request.getPaymentMethod())
                .amount(booking.getTotalAmount())
                .status("PROCESSING")
                .message("Payment is being processed")
                .build();
    }

    private void validateUserId(String userId) {
        if (userId == null || userId.isBlank()) {
            throw new BadRequestException("Authenticated user is required");
        }
    }

    private void validateRequest(PaymentInitRequest request) {
        if (request == null || request.getPaymentMethod() == null) {
            throw new BadRequestException("paymentMethod is required");
        }
    }
}
