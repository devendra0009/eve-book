package com.davendra.event_booking.modules.inventory.service;

import com.davendra.event_booking.modules.inventory.dtos.request.CreateSeatRequest;
import com.davendra.event_booking.modules.inventory.dtos.response.SeatResponse;
import com.davendra.event_booking.modules.inventory.entity.SeatEntity;
import org.springframework.data.domain.Page;

import java.util.List;

public interface SeatService {

    SeatEntity createSeat(CreateSeatRequest request);

    SeatEntity getSeatById(Long id);

    SeatResponse getSeatResponseById(Long id);

    Page<SeatResponse> getAllSeats(int page, int size);

    List<SeatEntity> getSeatsByHallId(Long hallId);
}
