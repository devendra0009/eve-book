package com.davendra.event_booking.modules.event.service;

import com.davendra.event_booking.modules.event.dtos.req.CreateShowRequest;
import com.davendra.event_booking.modules.event.dtos.response.ShowResponse;
import com.davendra.event_booking.modules.event.entity.ShowEntity;
import org.springframework.data.domain.Page;

public interface ShowService {

    ShowEntity createShow(CreateShowRequest request);

    ShowEntity getShowById(Long id);

    ShowResponse getShowResponseById(Long id);

    Page<ShowResponse> getAllShows(int page, int size);
}
