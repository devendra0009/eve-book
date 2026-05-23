package com.davendra.event_booking.modules.booking.cache;

import com.github.benmanes.caffeine.cache.Cache;
import com.github.benmanes.caffeine.cache.Caffeine;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;

@Component
public class SeatReservationCacheFacadeImpl implements SeatReservationCacheFacade {

    private static final int RESERVATION_TTL_MINUTES = 5;

    private final Cache<String, String> reservationCache = Caffeine.newBuilder()
            .expireAfterWrite(RESERVATION_TTL_MINUTES, TimeUnit.MINUTES)
            .build();

    @Override
    public boolean isHeldByAnotherUser(Long showId, Long seatId, String userId) {
        String holder = reservationCache.getIfPresent(cacheKey(showId, seatId));
        return holder != null && !holder.equals(userId);
    }

    @Override
    public void markReserved(Long showId, Long seatId, String userId) {
        reservationCache.put(cacheKey(showId, seatId), userId);
    }

    @Override
    public void release(Long showId, Long seatId) {
        reservationCache.invalidate(cacheKey(showId, seatId));
    }

    private String cacheKey(Long showId, Long seatId) {
        return showId + ":" + seatId;
    }
}
