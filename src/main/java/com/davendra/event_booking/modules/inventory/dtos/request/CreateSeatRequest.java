package com.davendra.event_booking.modules.inventory.dtos.request;

import lombok.Data;

@Data
public class CreateSeatRequest {
    private String seatNumber;
    private String rowNumber;
    private Integer columnNumber;
    private String seatType;
    private Long hallId;
}
