package com.davendra.event_booking.modules.payment.strategy;

import com.davendra.event_booking.modules.payment.enums.PaymentMethodType;
import com.davendra.event_booking.modules.payment.model.PaymentContext;
import com.davendra.event_booking.modules.payment.model.PaymentResult;

public class CreditCardPaymentStrategy implements PaymentStrategy {

    @Override
    public PaymentMethodType getPaymentMethodType() {
        return PaymentMethodType.CREDIT_CARD;
    }

    @Override
    public PaymentResult process(PaymentContext context) {
        throw new UnsupportedOperationException("Credit card payments are not implemented yet");
    }
}
