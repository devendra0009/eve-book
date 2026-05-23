package com.davendra.event_booking.modules.booking.dtos.response;

import com.davendra.event_booking.modules.booking.enums.BookingStatus;
import com.davendra.event_booking.modules.booking.enums.PaymentStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BookingReservationResponse {

    private Long bookingId;
    private Long showId;
    private String userId;
    private BigDecimal totalAmount;
    private BookingStatus bookingStatus;
    private PaymentStatus paymentStatus;
    private LocalDateTime expiresAt;
    private List<BookingSeatResponse> seats;
}
