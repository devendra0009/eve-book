package com.davendra.event_booking.modules.inventory.service.impl;

import com.davendra.event_booking.common.exception.ResourceNotFoundException;
import com.davendra.event_booking.modules.inventory.dtos.request.CreateSeatRequest;
import com.davendra.event_booking.modules.inventory.dtos.response.SeatResponse;
import com.davendra.event_booking.modules.inventory.entity.SeatEntity;
import com.davendra.event_booking.modules.inventory.enums.SeatStatus;
import com.davendra.event_booking.modules.inventory.enums.SeatType;
import com.davendra.event_booking.modules.inventory.mapper.SeatMapper;
import com.davendra.event_booking.modules.inventory.repo.SeatRepository;
import com.davendra.event_booking.modules.inventory.service.SeatService;
import com.davendra.event_booking.modules.venue.entity.HallEntity;
import com.davendra.event_booking.modules.venue.service.HallService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class SeatServiceImpl implements SeatService {

    private final SeatRepository seatRepository;
    private final HallService hallService;
    private final SeatMapper seatMapper;

    @Override
    public SeatEntity createSeat(CreateSeatRequest request) {
        HallEntity hall = hallService.getHallById(request.getHallId());

        SeatEntity seat = SeatEntity.builder()
                .seatNumber(request.getSeatNumber())
                .rowNumber(request.getRowNumber())
                .columnNumber(request.getColumnNumber())
                .seatType(SeatType.valueOf(request.getSeatType()))
                .status(SeatStatus.AVAILABLE)
                .hall(hall)
                .build();

        return seatRepository.save(seat);
    }

    @Override
    public SeatEntity getSeatById(Long id) {
        return seatRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Seat not found with id: " + id));
    }

    @Override
    @Transactional(readOnly = true)
    public SeatResponse getSeatResponseById(Long id) {
        return seatMapper.toResponse(getSeatById(id));
    }

    @Override
    @Transactional(readOnly = true)
    public Page<SeatResponse> getAllSeats(int page, int size) {
        return seatRepository.findAll(PageRequest.of(page, size))
                .map(seatMapper::toResponse);
    }

    @Override
    @Transactional(readOnly = true)
    public List<SeatEntity> getSeatsByHallId(Long hallId) {
        return seatRepository.findByHall_Id(hallId);
    }
}
