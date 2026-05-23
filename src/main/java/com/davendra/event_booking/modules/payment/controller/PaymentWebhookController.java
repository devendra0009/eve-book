package com.davendra.event_booking.modules.payment.controller;

import com.davendra.event_booking.common.exception.BadRequestException;
import com.davendra.event_booking.common.handler.GlobalResponseHandler;
import com.davendra.event_booking.common.response.ApiResponse;
import com.davendra.event_booking.modules.payment.webhook.PaymentWebhookHandler;
import com.davendra.event_booking.modules.payment.webhook.PaymentWebhookPayload;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/webhooks/payment")
@RequiredArgsConstructor
public class PaymentWebhookController {

    private final PaymentWebhookHandler paymentWebhookHandler;

    @Value("${payment.webhook.secret}")
    private String webhookSecret;

    @PostMapping
    public ResponseEntity<ApiResponse<Void>> handleWebhook(
            @RequestBody PaymentWebhookPayload payload,
            @RequestHeader("X-Webhook-Secret") String secret
    ) {
        if (!webhookSecret.equals(secret)) {
            throw new BadRequestException("Invalid webhook secret");
        }

        paymentWebhookHandler.handle(payload);
        return GlobalResponseHandler.success(null, HttpStatus.OK);
    }
}
