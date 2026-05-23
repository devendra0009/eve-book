package com.davendra.event_booking.modules.event.mapper;

import com.davendra.event_booking.modules.event.dtos.response.ShowResponse;
import com.davendra.event_booking.modules.event.entity.ShowEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface ShowMapper {

    @Mapping(source = "event.id", target = "eventId")
    @Mapping(source = "hall.id", target = "hallId")
    ShowResponse toResponse(ShowEntity entity);
}
