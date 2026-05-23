package com.davendra.event_booking.modules.event.controller;

import com.davendra.event_booking.common.handler.GlobalResponseHandler;
import com.davendra.event_booking.common.response.ApiResponse;
import com.davendra.event_booking.modules.event.dtos.req.CreateEventRequest;
import com.davendra.event_booking.modules.event.dtos.response.EventResponse;
import com.davendra.event_booking.modules.event.mapper.EventMapper;
import com.davendra.event_booking.modules.event.service.EventService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/events")
@RequiredArgsConstructor
public class EventController {

    private final EventService eventService;
    private final EventMapper eventMapper;

    @PostMapping
    public ResponseEntity<ApiResponse<EventResponse>> createEvent(
            @RequestBody CreateEventRequest request
    ) {
        return GlobalResponseHandler.success(
                eventMapper.toResponse(eventService.createEvent(request)),
                HttpStatus.CREATED
        );
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<EventResponse>> getEventById(@PathVariable Long id) {
        return GlobalResponseHandler.success(eventService.getEventResponseById(id));
    }

    @GetMapping
    public ResponseEntity<ApiResponse<List<EventResponse>>> getAllEvents(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    ) {
        return GlobalResponseHandler.success(eventService.getAllEvents(page, size));
    }
}
