package com.davendra.event_booking.modules.booking.cache;

public interface SeatReservationCacheFacade {

    boolean isHeldByAnotherUser(Long showId, Long seatId, String userId);

    void markReserved(Long showId, Long seatId, String userId);

    void release(Long showId, Long seatId);
}
