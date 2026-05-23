package com.davendra.event_booking.modules.event.service;

import com.davendra.event_booking.modules.event.dtos.req.CreateEventRequest;
import com.davendra.event_booking.modules.event.dtos.response.EventResponse;
import com.davendra.event_booking.modules.event.entity.EventEntity;
import org.springframework.data.domain.Page;

public interface EventService {

    EventEntity createEvent(CreateEventRequest request);

    EventEntity getEventById(Long id);

    EventResponse getEventResponseById(Long id);

    Page<EventResponse> getAllEvents(int page, int size);
}
