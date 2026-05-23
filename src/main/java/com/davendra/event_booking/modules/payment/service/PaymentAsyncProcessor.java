package com.davendra.event_booking.modules.payment.service;

import com.davendra.event_booking.modules.payment.model.PaymentContext;
import com.davendra.event_booking.modules.payment.model.PaymentResult;
import com.davendra.event_booking.modules.payment.strategy.PaymentStrategy;
import com.davendra.event_booking.modules.payment.strategy.PaymentStrategyRegistry;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class PaymentAsyncProcessor {

    private final PaymentStrategyRegistry paymentStrategyRegistry;

    @Async
    public void process(PaymentContext context) {
        PaymentStrategy strategy = paymentStrategyRegistry.getStrategy(context.getPaymentMethod());
        PaymentResult result = strategy.process(context);

        if (!result.isSuccess()) {
            log.warn("Payment failed for bookingId={}: {}", context.getBookingId(), result.getMessage());
        }
    }
}
