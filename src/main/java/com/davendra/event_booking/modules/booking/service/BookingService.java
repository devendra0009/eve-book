package com.davendra.event_booking.modules.booking.service;

import com.davendra.event_booking.modules.booking.dtos.request.BookingReservationRequest;
import com.davendra.event_booking.modules.booking.dtos.response.BookingReservationResponse;

public interface BookingService {

    BookingReservationResponse reserveSeats(BookingReservationRequest request, String userId);

    BookingReservationResponse getBooking(Long bookingId, String userId);

    BookingReservationResponse confirmBooking(Long bookingId, String userId);

    BookingReservationResponse cancelBooking(Long bookingId, String userId);

    void expireBooking(Long bookingId);
}
