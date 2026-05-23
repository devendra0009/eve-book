package com.davendra.event_booking.modules.payment.model;

import com.davendra.event_booking.modules.payment.enums.PaymentMethodType;
import lombok.Builder;
import lombok.Value;

import java.math.BigDecimal;

@Value
@Builder
public class PaymentContext {

    Long bookingId;
    String userId;
    BigDecimal amount;
    PaymentMethodType paymentMethod;
}
