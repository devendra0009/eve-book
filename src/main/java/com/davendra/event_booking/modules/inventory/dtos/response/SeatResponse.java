package com.davendra.event_booking.modules.inventory.dtos.response;

import com.davendra.event_booking.modules.inventory.enums.SeatStatus;
import com.davendra.event_booking.modules.inventory.enums.SeatType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SeatResponse {

    private Long id;
    private String seatNumber;
    private String rowNumber;
    private Integer columnNumber;
    private SeatType seatType;
    private SeatStatus status;
    private Long hallId;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
