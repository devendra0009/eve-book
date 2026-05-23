package com.davendra.event_booking.modules.event.service;

import com.davendra.event_booking.modules.event.dtos.req.CreateEventRequest;
import com.davendra.event_booking.modules.event.entity.EventEntity;
import com.davendra.event_booking.modules.event.enums.EventType;
import com.davendra.event_booking.modules.event.repo.EventRepository;
import com.davendra.event_booking.modules.venue.entity.VenueEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class EventServiceImpl {

    private final EventRepository eventRepository;
    private final VenueRepository venueRepository;

    public EventEntity createEvent(CreateEventRequest request) {

        VenueEntity venue = venueRepository.findById(
                request.getVenueId()
        ).orElseThrow(() ->
                new RuntimeException("Venue not found")
        );

        EventEntity event = EventEntity.builder()
                .title(request.getTitle())
                .description(request.getDescription())
                .duration(request.getDuration())
                .language(request.getLanguage())
                .genre(request.getGenre())
                .organizedBy(request.getOrganizedBy())
                .eventType(EventType.valueOf(request.getEventType()))
                .posterUrl(request.getPosterUrl())
                .bannerUrl(request.getBannerUrl())
                .venue(venue)
                .createdAt(LocalDateTime.now())
                .active(true)
                .bookingEnabled(true)
                .build();

        return eventRepository.save(event);
    }
}