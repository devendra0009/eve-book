package com.davendra.event_booking.modules.venue.repo;

import com.davendra.event_booking.modules.venue.entity.VenueEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface VenueRepository
        extends JpaRepository<VenueEntity, Long> {
}