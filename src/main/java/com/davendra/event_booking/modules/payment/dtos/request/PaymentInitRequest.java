package com.davendra.event_booking.modules.payment.dtos.request;

import com.davendra.event_booking.modules.payment.enums.PaymentMethodType;
import lombok.Data;

@Data
public class PaymentInitRequest {

    private PaymentMethodType paymentMethod;
}
