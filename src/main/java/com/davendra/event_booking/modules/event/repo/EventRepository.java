package com.davendra.event_booking.modules.event.repo;

import com.davendra.event_booking.modules.event.entity.EventEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EventRepository extends JpaRepository<EventEntity, Long> {
}
