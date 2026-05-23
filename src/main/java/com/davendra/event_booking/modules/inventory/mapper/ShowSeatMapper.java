package com.davendra.event_booking.modules.inventory.mapper;

import com.davendra.event_booking.modules.inventory.dtos.response.ShowSeatResponse;
import com.davendra.event_booking.modules.inventory.entity.ShowSeatEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface ShowSeatMapper {

    @Mapping(source = "show.id", target = "showId")
    @Mapping(source = "seat.id", target = "seatId")
    @Mapping(source = "seat.seatNumber", target = "seatNumber")
    ShowSeatResponse toResponse(ShowSeatEntity entity);
}
