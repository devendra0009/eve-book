package com.davendra.event_booking.modules.booking.repo;

import com.davendra.event_booking.modules.booking.entity.BookingEntity;
import com.davendra.event_booking.modules.booking.enums.BookingStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface BookingRepository extends JpaRepository<BookingEntity, Long> {

    @Query("""
            SELECT DISTINCT b FROM BookingEntity b
            JOIN FETCH b.show
            JOIN FETCH b.seats s
            JOIN FETCH s.seat
            WHERE b.id = :id
            """)
    Optional<BookingEntity> findByIdWithSeats(@Param("id") Long id);

    @Query("""
            SELECT b.id FROM BookingEntity b
            WHERE b.bookingStatus = :status
              AND b.expiresAt < :now
            """)
    List<Long> findExpiredBookingIds(
            @Param("status") BookingStatus status,
            @Param("now") LocalDateTime now
    );
}
