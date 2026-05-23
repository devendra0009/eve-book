package com.davendra.event_booking.modules.event.dtos.response;

import com.davendra.event_booking.modules.event.enums.ShowStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ShowResponse {

    private Long id;
    private LocalDateTime startTime;
    private LocalDateTime endTime;
    private ShowStatus status;
    private Long eventId;
    private Long hallId;
    private Boolean bookingOpen;
    private Integer availableSeats;
    private LocalDateTime createdAt;
}
