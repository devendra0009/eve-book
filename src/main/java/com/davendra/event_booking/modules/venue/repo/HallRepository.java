package com.davendra.event_booking.modules.venue.repo;


import com.davendra.event_booking.modules.venue.entity.HallEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface HallRepository
        extends JpaRepository<HallEntity, Long> {
}
