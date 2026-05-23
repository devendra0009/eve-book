package com.davendra.event_booking.modules.venue.dtos.request;

import lombok.Data;

@Data
public class CreateVenueRequest {

    private String name;

    private String city;

    private String state;

    private Double latitude;

    private Double longitude;

    private Integer capacity;

    private String address;
}