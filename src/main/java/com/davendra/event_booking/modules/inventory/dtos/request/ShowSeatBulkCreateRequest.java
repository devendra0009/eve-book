package com.davendra.event_booking.modules.inventory.dtos.request;


import lombok.Data;

@Data
public class ShowSeatBulkCreateRequest {

    private Long showId;

    private Long hallId;
}