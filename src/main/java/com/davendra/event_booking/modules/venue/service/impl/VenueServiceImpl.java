package com.davendra.event_booking.modules.venue.service.impl;

import com.davendra.event_booking.common.exception.ResourceNotFoundException;
import com.davendra.event_booking.modules.venue.dtos.request.CreateVenueRequest;
import com.davendra.event_booking.modules.venue.dtos.response.VenueResponse;
import com.davendra.event_booking.modules.venue.entity.VenueEntity;
import com.davendra.event_booking.modules.venue.mapper.VenueMapper;
import com.davendra.event_booking.modules.venue.repo.VenueRepository;
import com.davendra.event_booking.modules.venue.service.VenueService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class VenueServiceImpl implements VenueService {

    private final VenueRepository venueRepository;
    private final VenueMapper venueMapper;

    @Override
    public VenueEntity createVenue(CreateVenueRequest request) {
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
                        new ResourceNotFoundException("Venue not found with id: " + id));
    }

    @Override
    @Transactional(readOnly = true)
    public VenueResponse getVenueResponseById(Long id) {
        return venueMapper.toResponse(getVenueById(id));
    }

    @Override
    @Transactional(readOnly = true)
    public Page<VenueResponse> getAllVenues(int page, int size) {
        return venueRepository.findAll(PageRequest.of(page, size))
                .map(venueMapper::toResponse);
    }
}
