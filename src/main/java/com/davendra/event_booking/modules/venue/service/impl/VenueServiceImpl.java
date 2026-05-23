package com.davendra.event_booking.modules.venue.service.impl;


import com.davendra.event_booking.modules.venue.entity.VenueEntity;
import com.davendra.event_booking.modules.venue.service.VenueService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class VenueServiceImpl implements VenueService {

    private final VenueRepository venueRepository;

    @Override
    public VenueEntity createVenue(
            CreateVenueRequest request
    ) {

        VenueEntity venue = VenueEntity.builder()
                .name(request.getName())
                .city(request.getCity())
                .state(request.getState())
                .latitude(request.getLatitude())
                .longitude(request.getLongitude())
                .capacity(request.getCapacity())
                .address(request.getAddress())
                .build();

        return venueRepository.save(venue);
    }

    @Override
    public VenueEntity getVenueById(Long id) {

        return venueRepository.findById(id)
                .orElseThrow(() ->
                        new RuntimeException("Venue not found"));
    }
}
