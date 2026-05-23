package com.davendra.event_booking.modules.venue.mapper;

import com.davendra.event_booking.modules.venue.dtos.response.VenueResponse;
import com.davendra.event_booking.modules.venue.entity.VenueEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface VenueMapper {

    VenueResponse toResponse(VenueEntity entity);
}
