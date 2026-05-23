package com.davendra.event_booking.modules.venue.service;

import com.davendra.event_booking.modules.venue.dtos.request.CreateVenueRequest;
import com.davendra.event_booking.modules.venue.dtos.response.VenueResponse;
import com.davendra.event_booking.modules.venue.entity.VenueEntity;
import org.springframework.data.domain.Page;

public interface VenueService {

    VenueEntity createVenue(CreateVenueRequest request);

    VenueEntity getVenueById(Long id);

    VenueResponse getVenueResponseById(Long id);

    Page<VenueResponse> getAllVenues(int page, int size);
}
