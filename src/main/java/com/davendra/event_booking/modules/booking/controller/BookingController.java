package com.davendra.event_booking.modules.booking.controller;

import com.davendra.event_booking.common.handler.GlobalResponseHandler;
import com.davendra.event_booking.common.response.ApiResponse;
import com.davendra.event_booking.modules.booking.dtos.request.BookingReservationRequest;
import com.davendra.event_booking.modules.booking.dtos.response.BookingReservationResponse;
import com.davendra.event_booking.modules.booking.service.BookingService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/booking")
@RequiredArgsConstructor
public class BookingController {

    private final BookingService bookingService;

    @GetMapping("/test")
    public ResponseEntity<ApiResponse<String>> reserveSeats(
    ) {
        return GlobalResponseHandler.success(
                "Test ok chnages!",
                HttpStatus.OK
        );
    }

    @PostMapping("/reservation")
    public ResponseEntity<ApiResponse<BookingReservationResponse>> reserveSeats(
            @RequestBody BookingReservationRequest request,
            HttpServletRequest httpRequest
    ) {
        String userId = (String) httpRequest.getAttribute("firebaseUid");
        return GlobalResponseHandler.success(
                bookingService.reserveSeats(request, userId),
                HttpStatus.CREATED
        );
    }

    @GetMapping("/{bookingId}")
    public ResponseEntity<ApiResponse<BookingReservationResponse>> getBooking(
            @PathVariable Long bookingId,
            HttpServletRequest httpRequest
    ) {
        String userId = (String) httpRequest.getAttribute("firebaseUid");
        return GlobalResponseHandler.success(
                bookingService.getBooking(bookingId, userId),
                HttpStatus.OK
        );
    }

    @PostMapping("/{bookingId}/confirm")
    public ResponseEntity<ApiResponse<BookingReservationResponse>> confirmBooking(
            @PathVariable Long bookingId,
            HttpServletRequest httpRequest
    ) {
        String userId = (String) httpRequest.getAttribute("firebaseUid");
        return GlobalResponseHandler.success(
                bookingService.confirmBooking(bookingId, userId),
                HttpStatus.OK
        );
    }

    @PostMapping("/{bookingId}/cancel")
    public ResponseEntity<ApiResponse<BookingReservationResponse>> cancelBooking(
            @PathVariable Long bookingId,
            HttpServletRequest httpRequest
    ) {
        String userId = (String) httpRequest.getAttribute("firebaseUid");
        return GlobalResponseHandler.success(
                bookingService.cancelBooking(bookingId, userId),
                HttpStatus.OK
        );
    }
}
