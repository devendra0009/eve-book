package com.davendra.event_booking.modules.booking.dtos.request;

import lombok.Data;

import java.util.List;

@Data
public class BookingReservationRequest {

    private Long showId;
    private List<Long> seatIds;
}
