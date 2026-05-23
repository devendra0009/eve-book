package com.davendra.event_booking.modules.search.repo;

import com.davendra.event_booking.modules.event.entity.EventEntity;
import com.davendra.event_booking.modules.search.dtos.req.SearchRequest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface SearchRepository {

    Page<EventEntity> searchEvents(SearchRequest request, Pageable pageable);
}
