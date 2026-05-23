package com.davendra.event_booking.modules.search.controller;

import com.davendra.event_booking.common.handler.GlobalResponseHandler;
import com.davendra.event_booking.common.response.ApiResponse;
import com.davendra.event_booking.modules.search.dtos.req.SearchRequest;
import com.davendra.event_booking.modules.search.dtos.response.SearchResponse;
import com.davendra.event_booking.modules.search.service.SearchService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/search")
@RequiredArgsConstructor
public class SearchController {

    private final SearchService searchService;

    @GetMapping
    public ResponseEntity<ApiResponse<List<SearchResponse>>> search(
            @ModelAttribute SearchRequest request) {
        return GlobalResponseHandler.success(searchService.search(request));
    }
}
