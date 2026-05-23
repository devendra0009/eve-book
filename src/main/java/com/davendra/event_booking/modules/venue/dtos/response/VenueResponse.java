package com.davendra.event_booking.modules.venue.dtos.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class VenueResponse {

    private Long id;
    private String name;
    private String city;
    private String state;
    private Double latitude;
    private Double longitude;
    private Integer capacity;
    private String address;
    private String pincode;
    private Boolean active;
}
