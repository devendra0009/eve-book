package com.davendra.event_booking.modules.booking.repo;

import com.davendra.event_booking.modules.booking.entity.BookingSeatEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BookingSeatRepository extends JpaRepository<BookingSeatEntity, Long> {
}
