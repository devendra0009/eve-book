package com.davendra.event_booking.modules.event.dtos.req;

import lombok.Data;

@Data
public class CreateShowRequest {

    private String startTime;

    private String endTime;

    private Long eventId;

    private Long hallId;
}