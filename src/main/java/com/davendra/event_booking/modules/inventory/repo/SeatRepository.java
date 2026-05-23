package com.davendra.event_booking.modules.inventory.repo;

import com.davendra.event_booking.modules.inventory.entity.SeatEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SeatRepository
        extends JpaRepository<SeatEntity, Long> {

    List<SeatEntity> findByHall_Id(Long hallId);
}