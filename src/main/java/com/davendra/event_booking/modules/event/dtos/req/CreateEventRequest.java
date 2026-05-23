package com.davendra.event_booking.modules.event.dtos.req;

import lombok.Data;

@Data
public class CreateEventRequest {

    private String title;

    private String description;

    private Integer duration;

    private String language;

    private String genre;

    private String organizedBy;

    private String eventType;

    private String posterUrl;

    private String bannerUrl;

    private Long venueId;
}