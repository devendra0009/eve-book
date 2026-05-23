package com.davendra.event_booking.common.exception;

public class SeatReservationConflictException extends RuntimeException {

    public SeatReservationConflictException(String message) {
        super(message);
    }
}
