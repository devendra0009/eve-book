package com.davendra.event_booking.modules.venue.dtos.request;

import lombok.Data;

@Data
public class CreateHallRequest {
    private String name;
    private Integer totalSeating;
    private Long venueId;
    private String screenType;
}
