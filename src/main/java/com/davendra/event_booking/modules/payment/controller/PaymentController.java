package com.davendra.event_booking.modules.payment.controller;

import com.davendra.event_booking.common.handler.GlobalResponseHandler;
import com.davendra.event_booking.common.response.ApiResponse;
import com.davendra.event_booking.modules.payment.dtos.request.PaymentInitRequest;
import com.davendra.event_booking.modules.payment.dtos.response.PaymentInitResponse;
import com.davendra.event_booking.modules.payment.service.PaymentService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/booking")
@RequiredArgsConstructor
public class PaymentController {

    private final PaymentService paymentService;

    @PostMapping("/{bookingId}/payment")
    public ResponseEntity<ApiResponse<PaymentInitResponse>> initiatePayment(
            @PathVariable Long bookingId,
            @RequestBody PaymentInitRequest request,
            HttpServletRequest httpRequest
    ) {
        String userId = (String) httpRequest.getAttribute("firebaseUid");
        return GlobalResponseHandler.success(
                paymentService.initiatePayment(bookingId, request, userId),
                HttpStatus.ACCEPTED
        );
    }
}
