package com.davendra.event_booking.modules.event.service;

import com.davendra.event_booking.common.exception.BadRequestException;
import com.davendra.event_booking.common.exception.ShowScheduleConflictException;
import com.davendra.event_booking.modules.event.repo.ShowRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
@RequiredArgsConstructor
public class ShowSchedulingValidator {

    private final ShowRepository showRepository;

    public void validate(Long hallId, LocalDateTime startTime, LocalDateTime endTime) {
        if (!endTime.isAfter(startTime)) {
            throw new BadRequestException("endTime must be after startTime");
        }

        if (showRepository.existsOverlappingShow(hallId, startTime, endTime, null)) {
            throw new ShowScheduleConflictException(
                    "Hall " + hallId + " is already booked between "
                            + startTime + " and " + endTime
            );
        }
    }
}
