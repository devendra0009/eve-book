package com.davendra.event_booking.modules.event.service.impl;

import com.davendra.event_booking.common.exception.ResourceNotFoundException;
import com.davendra.event_booking.modules.event.dtos.req.CreateShowRequest;
import com.davendra.event_booking.modules.event.dtos.response.ShowResponse;
import com.davendra.event_booking.modules.event.entity.EventEntity;
import com.davendra.event_booking.modules.event.entity.ShowEntity;
import com.davendra.event_booking.modules.event.enums.ShowStatus;
import com.davendra.event_booking.modules.event.mapper.ShowMapper;
import com.davendra.event_booking.modules.event.repo.ShowRepository;
import com.davendra.event_booking.modules.event.events.ShowCreatedEvent;
import com.davendra.event_booking.modules.event.service.EventService;
import com.davendra.event_booking.modules.event.service.ShowSchedulingValidator;
import com.davendra.event_booking.modules.event.service.ShowService;
import com.davendra.event_booking.modules.venue.entity.HallEntity;
import com.davendra.event_booking.modules.venue.service.HallService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
@Transactional
public class ShowServiceImpl implements ShowService {

    private final ShowRepository showRepository;
    private final EventService eventService;
    private final HallService hallService;
    private final ShowMapper showMapper;
    private final ApplicationEventPublisher eventPublisher;
    private final ShowSchedulingValidator showSchedulingValidator;

    @Override
    public ShowEntity createShow(CreateShowRequest request) {
        EventEntity event = eventService.getEventById(request.getEventId());
        HallEntity hall = hallService.getHallById(request.getHallId());

        LocalDateTime startTime = LocalDateTime.parse(request.getStartTime());
        LocalDateTime endTime = LocalDateTime.parse(request.getEndTime());
        showSchedulingValidator.validate(hall.getId(), startTime, endTime);

        ShowEntity show = ShowEntity.builder()
                .startTime(startTime)
                .endTime(endTime)
                .event(event)
                .hall(hall)
                .status(ShowStatus.ACTIVE)
                .bookingOpen(true)
                .createdAt(LocalDateTime.now())
                .build();

        ShowEntity saved = showRepository.save(show);
        eventPublisher.publishEvent(new ShowCreatedEvent(saved.getId(), hall.getId()));
        return saved;
    }

    @Override
    public ShowEntity getShowById(Long id) {
        return showRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Show not found with id: " + id));
    }

    @Override
    @Transactional(readOnly = true)
    public ShowResponse getShowResponseById(Long id) {
        return showMapper.toResponse(getShowById(id));
    }

    @Override
    @Transactional(readOnly = true)
    public Page<ShowResponse> getAllShows(int page, int size) {
        return showRepository.findAll(PageRequest.of(page, size))
                .map(showMapper::toResponse);
    }
}
