package com.davendra.event_booking.modules.event.mapper;

import com.davendra.event_booking.modules.event.dtos.response.EventResponse;
import com.davendra.event_booking.modules.event.entity.EventEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface EventMapper {

    @Mapping(source = "venue.id", target = "venueId")
    EventResponse toResponse(EventEntity entity);
}
