package com.davendra.event_booking.modules.venue.controller;

import com.davendra.event_booking.common.handler.GlobalResponseHandler;
import com.davendra.event_booking.common.response.ApiResponse;
import com.davendra.event_booking.modules.venue.dtos.request.CreateHallRequest;
import com.davendra.event_booking.modules.venue.dtos.response.HallResponse;
import com.davendra.event_booking.modules.venue.mapper.HallMapper;
import com.davendra.event_booking.modules.venue.service.HallService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/halls")
@RequiredArgsConstructor
public class HallController {

    private final HallService hallService;
    private final HallMapper hallMapper;

    @PostMapping
    public ResponseEntity<ApiResponse<HallResponse>> createHall(
            @RequestBody CreateHallRequest request
    ) {
        return GlobalResponseHandler.success(
                hallMapper.toResponse(hallService.createHall(request)),
                HttpStatus.CREATED
        );
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<HallResponse>> getHallById(@PathVariable Long id) {
        return GlobalResponseHandler.success(hallService.getHallResponseById(id));
    }

    @GetMapping
    public ResponseEntity<ApiResponse<List<HallResponse>>> getAllHalls(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    ) {
        return GlobalResponseHandler.success(hallService.getAllHalls(page, size));
    }
}
