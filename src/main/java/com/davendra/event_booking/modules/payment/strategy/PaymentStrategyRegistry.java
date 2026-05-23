package com.davendra.event_booking.modules.payment.strategy;

import com.davendra.event_booking.common.exception.BadRequestException;
import com.davendra.event_booking.modules.payment.enums.PaymentMethodType;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@Component
public class PaymentStrategyRegistry {

    private final Map<PaymentMethodType, PaymentStrategy> strategies;

    public PaymentStrategyRegistry(List<PaymentStrategy> strategyList) {
        this.strategies = strategyList.stream()
                .collect(Collectors.toMap(PaymentStrategy::getPaymentMethodType, Function.identity()));
    }

    public PaymentStrategy getStrategy(PaymentMethodType paymentMethodType) {
        PaymentStrategy strategy = strategies.get(paymentMethodType);
        if (strategy == null) {
            throw new BadRequestException("Payment method not supported: " + paymentMethodType);
        }
        return strategy;
    }
}
