package com.davendra.event_booking.modules.payment.webhook;

import com.davendra.event_booking.common.exception.BadRequestException;
import com.davendra.event_booking.modules.booking.service.BookingService;
import com.davendra.event_booking.modules.payment.enums.PaymentWebhookEventType;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PaymentWebhookHandler {

    private final BookingService bookingService;

    public void handle(PaymentWebhookPayload payload) {
        if (payload.getBookingId() == null || payload.getUserId() == null) {
            throw new BadRequestException("bookingId and userId are required in webhook payload");
        }

        if (payload.getEventType() == PaymentWebhookEventType.PAYMENT_SUCCEEDED) {
            bookingService.confirmBooking(payload.getBookingId(), payload.getUserId());
            return;
        }

        if (payload.getEventType() == PaymentWebhookEventType.PAYMENT_FAILED) {
            bookingService.cancelBooking(payload.getBookingId(), payload.getUserId());
        }
    }
}
