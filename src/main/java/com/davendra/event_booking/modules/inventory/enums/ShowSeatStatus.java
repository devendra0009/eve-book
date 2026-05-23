package com.davendra.event_booking.modules.inventory.enums;

public enum ShowSeatStatus {

    /**
     * Seat available for booking
     */
    AVAILABLE,

    /**
     * Temporarily locked
     * during payment flow
     */
    RESERVED,

    /**
     * Successfully booked
     */
    BOOKED,

    /**
     * Admin blocked
     */
    BLOCKED,

    /**
     * Technical issue
     */
    MAINTENANCE
}