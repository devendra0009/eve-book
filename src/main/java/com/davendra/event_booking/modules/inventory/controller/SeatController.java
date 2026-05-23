package com.davendra.event_booking.modules.inventory.controller;

import com.davendra.event_booking.common.handler.GlobalResponseHandler;
import com.davendra.event_booking.common.response.ApiResponse;
import com.davendra.event_booking.modules.inventory.dtos.request.CreateSeatRequest;
import com.davendra.event_booking.modules.inventory.dtos.response.SeatResponse;
import com.davendra.event_booking.modules.inventory.mapper.SeatMapper;
import com.davendra.event_booking.modules.inventory.service.SeatService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/seats")
@RequiredArgsConstructor
public class SeatController {

    private final SeatService seatService;
    private final SeatMapper seatMapper;

    @PostMapping
    public ResponseEntity<ApiResponse<SeatResponse>> createSeat(
            @RequestBody CreateSeatRequest request
    ) {
        return GlobalResponseHandler.success(
                seatMapper.toResponse(seatService.createSeat(request)),
                HttpStatus.CREATED
        );
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<SeatResponse>> getSeatById(@PathVariable Long id) {
        return GlobalResponseHandler.success(seatService.getSeatResponseById(id));
    }

    @GetMapping
    public ResponseEntity<ApiResponse<List<SeatResponse>>> getAllSeats(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    ) {
        return GlobalResponseHandler.success(seatService.getAllSeats(page, size));
    }
}
