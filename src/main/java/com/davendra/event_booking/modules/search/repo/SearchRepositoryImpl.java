package com.davendra.event_booking.modules.search.repo;

import com.davendra.event_booking.modules.event.entity.EventEntity;
import com.davendra.event_booking.modules.search.dtos.req.SearchRequest;
import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.Map;

@Repository
@RequiredArgsConstructor
public class SearchRepositoryImpl implements SearchRepository {

    private static final Map<String, String> SORT_COLUMNS = Map.of(
            "title", "e.title",
            "createdAt", "e.created_at",
            "language", "e.language",
            "genre", "e.genre"
    );

    private final EntityManager entityManager;

    @Override
    @SuppressWarnings("unchecked")
    public Page<EventEntity> searchEvents(SearchRequest request, Pageable pageable) {
        String orderBy = buildOrderBy(pageable.getSort());
        String dataSql = EventSearchNativeQuery.SELECT_EVENTS + orderBy;
        String countSql = EventSearchNativeQuery.COUNT_EVENTS;

        Query dataQuery = entityManager.createNativeQuery(dataSql, EventEntity.class);
        Query countQuery = entityManager.createNativeQuery(countSql);
        bindParams(dataQuery, request);
        bindParams(countQuery, request);

        dataQuery.setFirstResult((int) pageable.getOffset());
        dataQuery.setMaxResults(pageable.getPageSize());

        List<EventEntity> content = dataQuery.getResultList();
        long total = ((Number) countQuery.getSingleResult()).longValue();

        return new PageImpl<>(content, pageable, total);
    }

    private void bindParams(Query query, SearchRequest request) {
        query.setParameter("title", nullIfBlank(request.getTitle()));
        query.setParameter("language", nullIfBlank(request.getLanguage()));
        query.setParameter("genre", nullIfBlank(request.getGenre()));
        query.setParameter("location", nullIfBlank(request.getLocation()));
        query.setParameter("venue", nullIfBlank(request.getVenue()));
        query.setParameter("searchDate", request.getDate());
    }

    private static String nullIfBlank(String value) {
        return StringUtils.hasText(value) ? value.trim() : null;
    }

    private static String buildOrderBy(Sort sort) {
        Sort.Order order = sort.stream().findFirst()
                .orElse(Sort.Order.desc("createdAt"));
        String column = SORT_COLUMNS.getOrDefault(order.getProperty(), "e.created_at");
        String direction = order.isAscending() ? "ASC" : "DESC";
        return " ORDER BY " + column + " " + direction;
    }
}
