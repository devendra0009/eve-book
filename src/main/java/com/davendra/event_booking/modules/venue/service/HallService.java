package com.davendra.event_booking.modules.venue.service;

import com.davendra.event_booking.modules.venue.dtos.request.CreateHallRequest;
import com.davendra.event_booking.modules.venue.dtos.response.HallResponse;
import com.davendra.event_booking.modules.venue.entity.HallEntity;
import org.springframework.data.domain.Page;

public interface HallService {

    HallEntity createHall(CreateHallRequest request);

    HallEntity getHallById(Long id);

    HallResponse getHallResponseById(Long id);

    Page<HallResponse> getAllHalls(int page, int size);
}
