package com.davendra.event_booking.modules.payment.dtos.response;

import com.davendra.event_booking.modules.payment.enums.PaymentMethodType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PaymentInitResponse {

    private Long bookingId;
    private PaymentMethodType paymentMethod;
    private BigDecimal amount;
    private String status;
    private String message;
}
