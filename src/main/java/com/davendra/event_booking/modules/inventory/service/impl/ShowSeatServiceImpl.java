package com.davendra.event_booking.modules.inventory.service.impl;

import com.davendra.event_booking.common.exception.BadRequestException;
import com.davendra.event_booking.common.exception.ResourceNotFoundException;
import com.davendra.event_booking.modules.event.entity.ShowEntity;
import com.davendra.event_booking.modules.event.repo.ShowRepository;
import com.davendra.event_booking.modules.inventory.dtos.request.ShowSeatBulkCreateRequest;
import com.davendra.event_booking.modules.inventory.dtos.response.ShowSeatResponse;
import com.davendra.event_booking.modules.inventory.entity.SeatEntity;
import com.davendra.event_booking.modules.inventory.entity.ShowSeatEntity;
import com.davendra.event_booking.modules.inventory.enums.ShowSeatStatus;
import com.davendra.event_booking.modules.inventory.mapper.ShowSeatMapper;
import com.davendra.event_booking.modules.inventory.repo.ShowSeatRepository;
import com.davendra.event_booking.modules.inventory.service.SeatService;
import com.davendra.event_booking.modules.inventory.service.ShowSeatPricingService;
import com.davendra.event_booking.modules.inventory.service.ShowSeatService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class ShowSeatServiceImpl implements ShowSeatService {

    private final ShowSeatRepository showSeatRepository;
    private final ShowRepository showRepository;
    private final SeatService seatService;
    private final ShowSeatPricingService showSeatPricingService;
    private final ShowSeatMapper showSeatMapper;

    @Override
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void initializeForShow(Long showId, Long hallId) {
        if (showSeatRepository.existsByShow_Id(showId)) {
            log.debug("Show seats already exist for showId={}", showId);
            return;
        }

        ShowEntity show = showRepository.findByIdWithHallAndVenue(showId)
                .orElseThrow(() -> new ResourceNotFoundException("Show not found with id: " + showId));

        List<SeatEntity> seats = seatService.getSeatsByHallId(hallId);
        if (seats.isEmpty()) {
            throw new BadRequestException(
                    "No seats found for hall " + hallId + ". Create seats before scheduling a show.");
        }

        List<ShowSeatEntity> showSeats = seats.stream()
                .map(seat -> ShowSeatEntity.builder()
                        .show(show)
                        .seat(seat)
                        .price(showSeatPricingService.calculatePrice(show, seat))
                        .status(ShowSeatStatus.AVAILABLE)
                        .build())
                .toList();

        showSeatRepository.saveAll(showSeats);
        log.info("Initialized {} show seats for showId={} hallId={}", showSeats.size(), showId, hallId);
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void initializeShowSeats(ShowSeatBulkCreateRequest request) {
        initializeForShow(request.getShowId(), request.getHallId());
    }

    @Override
    @Transactional(readOnly = true)
    public ShowSeatResponse getShowSeatResponseById(Long id) {
        ShowSeatEntity entity = showSeatRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Show seat not found with id: " + id));
        return showSeatMapper.toResponse(entity);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<ShowSeatResponse> getAllShowSeats(int page, int size) {
        return showSeatRepository.findAll(PageRequest.of(page, size))
                .map(showSeatMapper::toResponse);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<ShowSeatResponse> getAllShowSeatsByShowId(Long showId, int page, int size) {
        if (!showRepository.existsById(showId)) {
            throw new ResourceNotFoundException("Show not found with id: " + showId);
        }
        return showSeatRepository.findByShow_Id(showId, PageRequest.of(page, size))
                .map(showSeatMapper::toResponse);
    }
}
