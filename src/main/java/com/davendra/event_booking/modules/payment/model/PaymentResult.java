package com.davendra.event_booking.modules.payment.model;

import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class PaymentResult {

    boolean success;
    String transactionId;
    String message;

    public static PaymentResult success(String transactionId) {
        return PaymentResult.builder()
                .success(true)
                .transactionId(transactionId)
                .message("Payment processed successfully")
                .build();
    }

    public static PaymentResult failure(String message) {
        return PaymentResult.builder()
                .success(false)
                .message(message)
                .build();
    }
}
