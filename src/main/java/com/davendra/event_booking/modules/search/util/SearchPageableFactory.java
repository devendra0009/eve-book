package com.davendra.event_booking.modules.search.util;

import com.davendra.event_booking.modules.search.dtos.req.SearchRequest;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import java.util.Map;

public final class SearchPageableFactory {

    private static final Map<String, String> SORT_FIELDS = Map.of(
            "title", "title",
            "createdAt", "createdAt",
            "language", "language",
            "genre", "genre"
    );

    private SearchPageableFactory() {
    }

    public static Pageable from(SearchRequest request) {
        String sortField = SORT_FIELDS.getOrDefault(request.getSortBy(), "createdAt");
        Sort.Direction direction = "asc".equalsIgnoreCase(request.getSortDir())
                ? Sort.Direction.ASC
                : Sort.Direction.DESC;
        return PageRequest.of(request.getPage(), request.getSize(), Sort.by(direction, sortField));
    }
}
