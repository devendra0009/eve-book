package com.davendra.event_booking.modules.payment.strategy;

import com.davendra.event_booking.modules.payment.enums.PaymentMethodType;
import com.davendra.event_booking.modules.payment.model.PaymentContext;
import com.davendra.event_booking.modules.payment.model.PaymentResult;

public interface PaymentStrategy {

    PaymentMethodType getPaymentMethodType();

    PaymentResult process(PaymentContext context);
}
