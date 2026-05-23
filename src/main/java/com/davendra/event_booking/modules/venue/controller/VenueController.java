package com.davendra.event_booking.modules.venue.controller;

import com.davendra.event_booking.common.handler.GlobalResponseHandler;
import com.davendra.event_booking.common.response.ApiResponse;
import com.davendra.event_booking.modules.venue.dtos.request.CreateVenueRequest;
import com.davendra.event_booking.modules.venue.dtos.response.VenueResponse;
import com.davendra.event_booking.modules.venue.mapper.VenueMapper;
import com.davendra.event_booking.modules.venue.service.VenueService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/venues")
@RequiredArgsConstructor
public class VenueController {

    private final VenueService venueService;
    private final VenueMapper venueMapper;

    @PostMapping
    public ResponseEntity<ApiResponse<VenueResponse>> createVenue(
            @RequestBody CreateVenueRequest request
    ) {
        return GlobalResponseHandler.success(
                venueMapper.toResponse(venueService.createVenue(request)),
                HttpStatus.CREATED
        );
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<VenueResponse>> getVenueById(@PathVariable Long id) {
        return GlobalResponseHandler.success(venueService.getVenueResponseById(id));
    }

    @GetMapping
    public ResponseEntity<ApiResponse<List<VenueResponse>>> getAllVenues(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    ) {
        return GlobalResponseHandler.success(venueService.getAllVenues(page, size));
    }
}
