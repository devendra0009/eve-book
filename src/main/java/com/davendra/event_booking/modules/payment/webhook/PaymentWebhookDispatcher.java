package com.davendra.event_booking.modules.payment.webhook;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;

@Slf4j
@Component
public class PaymentWebhookDispatcher {

    private final RestClient restClient;
    private final String webhookUrl;
    private final String webhookSecret;

    public PaymentWebhookDispatcher(
            RestClient.Builder restClientBuilder,
            @Value("${payment.webhook.url}") String webhookUrl,
            @Value("${payment.webhook.secret}") String webhookSecret
    ) {
        this.restClient = restClientBuilder.build();
        this.webhookUrl = webhookUrl;
        this.webhookSecret = webhookSecret;
    }

    public void dispatch(PaymentWebhookPayload payload) {
        log.info("Dispatching payment webhook for bookingId={} event={}",
                payload.getBookingId(), payload.getEventType());

        restClient.post()
                .uri(webhookUrl)
                .contentType(MediaType.APPLICATION_JSON)
                .header("X-Webhook-Secret", webhookSecret)
                .body(payload)
                .retrieve()
                .toBodilessEntity();
    }
}
