package com.davendra.event_booking.modules.inventory.listener;

import com.davendra.event_booking.modules.event.events.ShowCreatedEvent;
import com.davendra.event_booking.modules.inventory.service.ShowSeatService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.transaction.event.TransactionPhase;
import org.springframework.transaction.event.TransactionalEventListener;

@Slf4j
@Component
@RequiredArgsConstructor
public class ShowSeatInitializationListener {

    private final ShowSeatService showSeatService;

    @TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT)
    public void onShowCreated(ShowCreatedEvent event) {
        try {
            showSeatService.initializeForShow(event.showId(), event.hallId());
        } catch (Exception ex) {
            log.error(
                    "Failed to initialize show seats for showId={} hallId={}",
                    event.showId(),
                    event.hallId(),
                    ex
            );
            throw ex;
        }
    }
}
