package com.davendra.event_booking.modules.booking.mapper;

import com.davendra.event_booking.modules.booking.dtos.response.BookingReservationResponse;
import com.davendra.event_booking.modules.booking.dtos.response.BookingSeatResponse;
import com.davendra.event_booking.modules.booking.entity.BookingEntity;
import com.davendra.event_booking.modules.booking.entity.BookingSeatEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface BookingMapper {

    @Mapping(source = "id", target = "bookingId")
    @Mapping(source = "show.id", target = "showId")
    @Mapping(source = "seats", target = "seats")
    BookingReservationResponse toReservationResponse(BookingEntity entity);

    @Mapping(source = "show.id", target = "showId")
    @Mapping(source = "seat.id", target = "seatId")
    BookingSeatResponse toSeatResponse(BookingSeatEntity entity);

    List<BookingSeatResponse> toSeatResponses(List<BookingSeatEntity> entities);
}
