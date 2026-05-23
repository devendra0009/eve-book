package com.davendra.event_booking.modules.payment.service;

import com.davendra.event_booking.modules.payment.dtos.request.PaymentInitRequest;
import com.davendra.event_booking.modules.payment.dtos.response.PaymentInitResponse;

public interface PaymentService {

    PaymentInitResponse initiatePayment(Long bookingId, PaymentInitRequest request, String userId);
}
