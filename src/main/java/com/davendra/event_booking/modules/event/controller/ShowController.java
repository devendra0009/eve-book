package com.davendra.event_booking.modules.event.controller;

import com.davendra.event_booking.common.handler.GlobalResponseHandler;
import com.davendra.event_booking.common.response.ApiResponse;
import com.davendra.event_booking.modules.event.dtos.req.CreateShowRequest;
import com.davendra.event_booking.modules.event.dtos.response.ShowResponse;
import com.davendra.event_booking.modules.event.mapper.ShowMapper;
import com.davendra.event_booking.modules.event.service.ShowService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/shows")
@RequiredArgsConstructor
public class ShowController {

    private final ShowService showService;
    private final ShowMapper showMapper;

    @PostMapping
    public ResponseEntity<ApiResponse<ShowResponse>> createShow(
            @RequestBody CreateShowRequest request
    ) {
        return GlobalResponseHandler.success(
                showMapper.toResponse(showService.createShow(request)),
                HttpStatus.CREATED
        );
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<ShowResponse>> getShowById(@PathVariable Long id) {
        return GlobalResponseHandler.success(showService.getShowResponseById(id));
    }

    @GetMapping
    public ResponseEntity<ApiResponse<List<ShowResponse>>> getAllShows(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    ) {
        return GlobalResponseHandler.success(showService.getAllShows(page, size));
    }
}
