package com.davendra.event_booking.modules.inventory.controller;

import com.davendra.event_booking.common.handler.GlobalResponseHandler;
import com.davendra.event_booking.common.response.ApiResponse;
import com.davendra.event_booking.modules.inventory.dtos.request.ShowSeatBulkCreateRequest;
import com.davendra.event_booking.modules.inventory.dtos.response.ShowSeatResponse;
import com.davendra.event_booking.modules.inventory.service.ShowSeatService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/show-seats")
@RequiredArgsConstructor
public class ShowSeatController {

    private final ShowSeatService showSeatService;

    @PostMapping("/bulk")
    public ResponseEntity<ApiResponse<String>> initializeShowSeats(
            @RequestBody ShowSeatBulkCreateRequest request) {
        showSeatService.initializeShowSeats(request);
        return GlobalResponseHandler.success(
                "Show seats initialized",
                HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<ShowSeatResponse>> getShowSeatById(@PathVariable Long id) {
        return GlobalResponseHandler.success(showSeatService.getShowSeatResponseById(id));
    }

    @GetMapping
    public ResponseEntity<ApiResponse<List<ShowSeatResponse>>> getAllShowSeats(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        return GlobalResponseHandler.success(showSeatService.getAllShowSeats(page, size));
    }

    @GetMapping("/show/{showId}")
    public ResponseEntity<ApiResponse<List<ShowSeatResponse>>> getAllShowSeatsByShowId(
            @PathVariable Long showId,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        return GlobalResponseHandler.success(
                showSeatService.getAllShowSeatsByShowId(showId, page, size));
    }
}
