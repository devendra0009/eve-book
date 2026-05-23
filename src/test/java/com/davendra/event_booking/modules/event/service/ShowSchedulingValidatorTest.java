package com.davendra.event_booking.modules.event.service;

import com.davendra.event_booking.common.exception.BadRequestException;
import com.davendra.event_booking.common.exception.ShowScheduleConflictException;
import com.davendra.event_booking.modules.event.repo.ShowRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.ArgumentMatchers.isNull;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ShowSchedulingValidatorTest {

    @Mock
    private ShowRepository showRepository;

    @InjectMocks
    private ShowSchedulingValidator showSchedulingValidator;

    private static final Long HALL_ID = 1L;
    private static final LocalDateTime START = LocalDateTime.of(2026, 5, 20, 15, 0);
    private static final LocalDateTime END = LocalDateTime.of(2026, 5, 20, 17, 0);

    @Test
    void rejectsWhenEndTimeNotAfterStartTime() {
        assertThrows(
                BadRequestException.class,
                () -> showSchedulingValidator.validate(HALL_ID, END, START)
        );
    }

    @Test
    void rejectsWhenHallAlreadyBooked() {
        when(showRepository.existsOverlappingShow(eq(HALL_ID), eq(START), eq(END), isNull()))
                .thenReturn(true);

        assertThrows(
                ShowScheduleConflictException.class,
                () -> showSchedulingValidator.validate(HALL_ID, START, END)
        );
    }

    @Test
    void allowsAdjacentSlot() {
        LocalDateTime adjacentStart = LocalDateTime.of(2026, 5, 20, 17, 0);
        LocalDateTime adjacentEnd = LocalDateTime.of(2026, 5, 20, 19, 0);

        when(showRepository.existsOverlappingShow(eq(HALL_ID), eq(adjacentStart), eq(adjacentEnd), isNull()))
                .thenReturn(false);

        assertDoesNotThrow(
                () -> showSchedulingValidator.validate(HALL_ID, adjacentStart, adjacentEnd)
        );

        verify(showRepository).existsOverlappingShow(HALL_ID, adjacentStart, adjacentEnd, null);
    }

    @Test
    void allowsWhenNoOverlap() {
        when(showRepository.existsOverlappingShow(eq(HALL_ID), eq(START), eq(END), isNull()))
                .thenReturn(false);

        assertDoesNotThrow(() -> showSchedulingValidator.validate(HALL_ID, START, END));
    }

    @Test
    void detectsPartialOverlap() {
        LocalDateTime clashStart = LocalDateTime.of(2026, 5, 20, 16, 0);
        LocalDateTime clashEnd = LocalDateTime.of(2026, 5, 20, 18, 0);

        when(showRepository.existsOverlappingShow(eq(HALL_ID), eq(clashStart), eq(clashEnd), isNull()))
                .thenReturn(true);

        assertThrows(
                ShowScheduleConflictException.class,
                () -> showSchedulingValidator.validate(HALL_ID, clashStart, clashEnd)
        );
    }
}
