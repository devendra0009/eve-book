package com.davendra.event_booking.modules.venue.mapper;

import com.davendra.event_booking.modules.venue.dtos.response.HallResponse;
import com.davendra.event_booking.modules.venue.entity.HallEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface HallMapper {

    @Mapping(source = "venue.id", target = "venueId")
    HallResponse toResponse(HallEntity entity);
}
