package com.davendra.event_booking.modules.payment.strategy;

import com.davendra.event_booking.modules.payment.enums.PaymentMethodType;
import com.davendra.event_booking.modules.payment.enums.PaymentWebhookEventType;
import com.davendra.event_booking.modules.payment.model.PaymentContext;
import com.davendra.event_booking.modules.payment.model.PaymentResult;
import com.davendra.event_booking.modules.payment.webhook.PaymentWebhookDispatcher;
import com.davendra.event_booking.modules.payment.webhook.PaymentWebhookPayload;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
@RequiredArgsConstructor
public class DummyPaymentStrategy implements PaymentStrategy {

    private final PaymentWebhookDispatcher paymentWebhookDispatcher;

    @Value("${payment.dummy.delay-ms:5000}")
    private long delayMs;

    @Override
    public PaymentMethodType getPaymentMethodType() {
        return PaymentMethodType.DUMMY;
    }

    @Override
    public PaymentResult process(PaymentContext context) {
        try {
            Thread.sleep(delayMs);
        } catch (InterruptedException ex) {
            Thread.currentThread().interrupt();
            return PaymentResult.failure("Payment processing interrupted");
        }

        String transactionId = "DUMMY-" + UUID.randomUUID();

        paymentWebhookDispatcher.dispatch(PaymentWebhookPayload.builder()
                .bookingId(context.getBookingId())
                .userId(context.getUserId())
                .transactionId(transactionId)
                .eventType(PaymentWebhookEventType.PAYMENT_SUCCEEDED)
                .amount(context.getAmount())
                .build());

        return PaymentResult.success(transactionId);
    }
}
