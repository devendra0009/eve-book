package com.davendra.event_booking.modules.search.service;

import com.davendra.event_booking.modules.search.dtos.req.SearchRequest;
import com.davendra.event_booking.modules.search.dtos.response.SearchResponse;
import org.springframework.data.domain.Page;

public interface SearchService {

    Page<SearchResponse> search(SearchRequest request);
}
