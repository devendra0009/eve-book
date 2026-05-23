package com.davendra.event_booking.modules.inventory.repo;

import com.davendra.event_booking.modules.inventory.entity.ShowSeatEntity;
import com.davendra.event_booking.modules.inventory.enums.ShowSeatStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import jakarta.persistence.LockModeType;

import java.time.LocalDateTime;
import java.util.Optional;

public interface ShowSeatRepository
        extends JpaRepository<ShowSeatEntity, Long> {

    boolean existsByShow_Id(Long showId);

    Page<ShowSeatEntity> findByShow_Id(Long showId, Pageable pageable);

    @Lock(LockModeType.PESSIMISTIC_WRITE)
    @Query("SELECT ss FROM ShowSeatEntity ss WHERE ss.show.id = :showId AND ss.seat.id = :seatId")
    Optional<ShowSeatEntity> findForUpdateByShowIdAndSeatId(
            @Param("showId") Long showId,
            @Param("seatId") Long seatId
    );

    @Modifying(clearAutomatically = true, flushAutomatically = true)
    @Query("""
            UPDATE ShowSeatEntity ss
            SET ss.status = :status,
                ss.lockedByUser = :lockedByUser,
                ss.lockedUntil = :lockedUntil
            WHERE ss.id = :id
              AND ss.status = :expectedStatus
            """)
    int lockSeatIfAvailable(
            @Param("id") Long id,
            @Param("status") ShowSeatStatus status,
            @Param("expectedStatus") ShowSeatStatus expectedStatus,
            @Param("lockedByUser") String lockedByUser,
            @Param("lockedUntil") LocalDateTime lockedUntil
    );

    @Modifying(clearAutomatically = true, flushAutomatically = true)
    @Query("""
            UPDATE ShowSeatEntity ss
            SET ss.status = :status,
                ss.lockedByUser = :lockedByUser,
                ss.lockedUntil = :lockedUntil
            WHERE ss.id = :id
              AND ss.status = :expectedStatus
              AND (ss.lockedUntil IS NULL OR ss.lockedUntil < :now)
            """)
    int reclaimExpiredReservation(
            @Param("id") Long id,
            @Param("status") ShowSeatStatus status,
            @Param("expectedStatus") ShowSeatStatus expectedStatus,
            @Param("lockedByUser") String lockedByUser,
            @Param("lockedUntil") LocalDateTime lockedUntil,
            @Param("now") LocalDateTime now
    );

    @Modifying(clearAutomatically = true, flushAutomatically = true)
    @Query("""
            UPDATE ShowSeatEntity ss
            SET ss.status = :availableStatus,
                ss.lockedByUser = null,
                ss.lockedUntil = null,
                ss.releasedAt = :releasedAt
            WHERE ss.show.id = :showId
              AND ss.seat.id = :seatId
              AND ss.status = :reservedStatus
              AND ss.lockedByUser = :lockedByUser
            """)
    int releaseSeatIfReservedByUser(
            @Param("showId") Long showId,
            @Param("seatId") Long seatId,
            @Param("lockedByUser") String lockedByUser,
            @Param("availableStatus") ShowSeatStatus availableStatus,
            @Param("reservedStatus") ShowSeatStatus reservedStatus,
            @Param("releasedAt") LocalDateTime releasedAt
    );

    @Modifying(clearAutomatically = true, flushAutomatically = true)
    @Query("""
            UPDATE ShowSeatEntity ss
            SET ss.status = :bookedStatus,
                ss.lockedByUser = null,
                ss.lockedUntil = null
            WHERE ss.show.id = :showId
              AND ss.seat.id = :seatId
              AND ss.status = :reservedStatus
              AND ss.lockedByUser = :lockedByUser
            """)
    int confirmSeatIfReservedByUser(
            @Param("showId") Long showId,
            @Param("seatId") Long seatId,
            @Param("lockedByUser") String lockedByUser,
            @Param("bookedStatus") ShowSeatStatus bookedStatus,
            @Param("reservedStatus") ShowSeatStatus reservedStatus
    );
}