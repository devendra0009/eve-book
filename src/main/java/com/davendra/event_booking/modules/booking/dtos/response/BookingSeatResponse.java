package com.davendra.event_booking.modules.booking.dtos.response;

import com.davendra.event_booking.modules.booking.enums.BookingSeatStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BookingSeatResponse {

    private Long id;
    private Long showId;
    private Long seatId;
    private BigDecimal price;
    private BigDecimal amount;
    private BookingSeatStatus status;
}
