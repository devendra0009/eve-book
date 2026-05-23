package com.davendra.event_booking.modules.inventory.service;

import com.davendra.event_booking.modules.inventory.dtos.request.ShowSeatBulkCreateRequest;
import com.davendra.event_booking.modules.inventory.dtos.response.ShowSeatResponse;
import org.springframework.data.domain.Page;

public interface ShowSeatService {

    void initializeForShow(Long showId, Long hallId);

    void initializeShowSeats(ShowSeatBulkCreateRequest request);

    ShowSeatResponse getShowSeatResponseById(Long id);

    Page<ShowSeatResponse> getAllShowSeats(int page, int size);

    Page<ShowSeatResponse> getAllShowSeatsByShowId(Long showId, int page, int size);
}
