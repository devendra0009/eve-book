package com.davendra.event_booking.modules.inventory.mapper;

import com.davendra.event_booking.modules.inventory.dtos.response.SeatResponse;
import com.davendra.event_booking.modules.inventory.entity.SeatEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface SeatMapper {

    @Mapping(source = "hall.id", target = "hallId")
    SeatResponse toResponse(SeatEntity entity);
}
