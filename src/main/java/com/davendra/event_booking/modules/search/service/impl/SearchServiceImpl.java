package com.davendra.event_booking.modules.search.service.impl;

import com.davendra.event_booking.modules.event.entity.EventEntity;
import com.davendra.event_booking.modules.event.entity.ShowEntity;
import com.davendra.event_booking.modules.event.repo.ShowRepository;
import com.davendra.event_booking.modules.search.dtos.req.SearchRequest;
import com.davendra.event_booking.modules.search.dtos.response.SearchResponse;
import com.davendra.event_booking.modules.search.mapper.SearchMapper;
import com.davendra.event_booking.modules.search.repo.SearchRepository;
import com.davendra.event_booking.modules.search.service.SearchService;
import com.davendra.event_booking.modules.search.util.SearchPageableFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class SearchServiceImpl implements SearchService {

    private final SearchRepository searchRepository;
    private final ShowRepository showRepository;
    private final SearchMapper searchMapper;

    @Override
    @Transactional(readOnly = true)
    public Page<SearchResponse> search(SearchRequest request) {
        Pageable pageable = SearchPageableFactory.from(request);
        Page<EventEntity> events = searchRepository.searchEvents(request, pageable);
        Map<Long, ShowEntity> showByEventId = resolveShowsForDate(events.getContent(), request);

        List<SearchResponse> content = events.getContent().stream()
                .map(event -> searchMapper.toResponse(event, showByEventId.get(event.getId())))
                .toList();

        return new PageImpl<>(content, pageable, events.getTotalElements());
    }

    private Map<Long, ShowEntity> resolveShowsForDate(List<EventEntity> events, SearchRequest request) {
        if (request.getDate() == null || events.isEmpty()) {
            return Map.of();
        }

        List<Long> eventIds = events.stream().map(EventEntity::getId).toList();
        LocalDateTime dayStart = request.getDate().atStartOfDay();
        LocalDateTime dayEnd = request.getDate().atTime(LocalTime.MAX);

        List<ShowEntity> shows = showRepository.findByEventIdInAndShowDate(eventIds, dayStart, dayEnd);

        Map<Long, ShowEntity> showByEventId = new HashMap<>();
        for (ShowEntity show : shows) {
            showByEventId.putIfAbsent(show.getEvent().getId(), show);
        }
        return showByEventId;
    }
}
