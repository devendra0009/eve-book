package com.davendra.event_booking.modules.inventory.dtos.response;

import com.davendra.event_booking.modules.inventory.enums.ShowSeatStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ShowSeatResponse {

    private Long id;
    private Long showId;
    private Long seatId;
    private String seatNumber;
    private BigDecimal price;
    private ShowSeatStatus status;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
