package com.davendra.event_booking.modules.event.service.impl;

import com.davendra.event_booking.common.exception.ResourceNotFoundException;
import com.davendra.event_booking.modules.event.dtos.req.CreateEventRequest;
import com.davendra.event_booking.modules.event.dtos.response.EventResponse;
import com.davendra.event_booking.modules.event.entity.EventEntity;
import com.davendra.event_booking.modules.event.enums.EventType;
import com.davendra.event_booking.modules.event.mapper.EventMapper;
import com.davendra.event_booking.modules.event.repo.EventRepository;
import com.davendra.event_booking.modules.event.service.EventService;
import com.davendra.event_booking.modules.venue.entity.VenueEntity;
import com.davendra.event_booking.modules.venue.service.VenueService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
@Transactional
public class EventServiceImpl implements EventService {

    private final EventRepository eventRepository;
    private final VenueService venueService;
    private final EventMapper eventMapper;

    @Override
    public EventEntity createEvent(CreateEventRequest request) {
        VenueEntity venue = venueService.getVenueById(request.getVenueId());

        EventEntity event = EventEntity.builder()
                .title(request.getTitle())
                .description(request.getDescription())
                .duration(request.getDuration())
                .language(request.getLanguage())
                .genre(request.getGenre())
                .organizedBy(request.getOrganizedBy())
                .eventType(EventType.valueOf(request.getEventType()))
                .posterUrl(request.getPosterUrl())
                .venue(venue)
                .createdAt(LocalDateTime.now())
                .build();

        return eventRepository.save(event);
    }

    @Override
    public EventEntity getEventById(Long id) {
        return eventRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Event not found with id: " + id));
    }

    @Override
    @Transactional(readOnly = true)
    public EventResponse getEventResponseById(Long id) {
        return eventMapper.toResponse(getEventById(id));
    }

    @Override
    @Transactional(readOnly = true)
    public Page<EventResponse> getAllEvents(int page, int size) {
        return eventRepository.findAll(PageRequest.of(page, size))
                .map(eventMapper::toResponse);
    }
}
