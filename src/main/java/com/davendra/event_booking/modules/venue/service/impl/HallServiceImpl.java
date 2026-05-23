package com.davendra.event_booking.modules.venue.service.impl;

import com.davendra.event_booking.common.exception.ResourceNotFoundException;
import com.davendra.event_booking.modules.venue.dtos.request.CreateHallRequest;
import com.davendra.event_booking.modules.venue.dtos.response.HallResponse;
import com.davendra.event_booking.modules.venue.entity.HallEntity;
import com.davendra.event_booking.modules.venue.entity.VenueEntity;
import com.davendra.event_booking.modules.venue.mapper.HallMapper;
import com.davendra.event_booking.modules.venue.repo.HallRepository;
import com.davendra.event_booking.modules.venue.service.HallService;
import com.davendra.event_booking.modules.venue.service.VenueService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class HallServiceImpl implements HallService {

    private final HallRepository hallRepository;
    private final VenueService venueService;
    private final HallMapper hallMapper;

    @Override
    public HallEntity createHall(CreateHallRequest request) {
        VenueEntity venue = venueService.getVenueById(request.getVenueId());

        HallEntity hall = HallEntity.builder()
                .name(request.getName())
                .totalSeating(request.getTotalSeating())
                .screenType(request.getScreenType())
                .venue(venue)
                .build();

        return hallRepository.save(hall);
    }

    @Override
    public HallEntity getHallById(Long id) {
        return hallRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Hall not found with id: " + id));
    }

    @Override
    @Transactional(readOnly = true)
    public HallResponse getHallResponseById(Long id) {
        return hallMapper.toResponse(getHallById(id));
    }

    @Override
    @Transactional(readOnly = true)
    public Page<HallResponse> getAllHalls(int page, int size) {
        return hallRepository.findAll(PageRequest.of(page, size))
                .map(hallMapper::toResponse);
    }
}
