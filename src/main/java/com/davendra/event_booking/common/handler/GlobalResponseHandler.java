package com.davendra.event_booking.common.handler;

import com.davendra.event_booking.common.response.ApiResponse;
import com.davendra.event_booking.common.response.PageMeta;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.function.Function;

public final class GlobalResponseHandler {

    private GlobalResponseHandler() {
    }

    public static <T> ResponseEntity<ApiResponse<T>> success(T data) {
        return ResponseEntity.ok(ApiResponse.of(data));
    }

    public static <T> ResponseEntity<ApiResponse<T>> success(T data, HttpStatus status) {
        return ResponseEntity.status(status).body(ApiResponse.of(data));
    }

    public static <E, R> ResponseEntity<ApiResponse<List<R>>> success(
            Page<E> page,
            Function<E, R> mapper
    ) {
        List<R> content = page.getContent().stream().map(mapper).toList();
        return ResponseEntity.ok(
                ApiResponse.of(content, PageMeta.from(page))
        );
    }

    public static <R> ResponseEntity<ApiResponse<List<R>>> success(Page<R> page) {
        return ResponseEntity.ok(
                ApiResponse.of(page.getContent(), PageMeta.from(page))
        );
    }
}
