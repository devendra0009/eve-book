package com.davendra.event_booking.modules.venue.dtos.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class HallResponse {

    private Long id;
    private String name;
    private Integer totalSeating;
    private Long venueId;
    private String screenType;
    private Boolean active;
}
