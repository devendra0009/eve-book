package com.davendra.event_booking.modules.payment.webhook;

import com.davendra.event_booking.modules.payment.enums.PaymentWebhookEventType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PaymentWebhookPayload {

    private Long bookingId;
    private String userId;
    private String transactionId;
    private PaymentWebhookEventType eventType;
    private BigDecimal amount;
}
