package com.davendra.event_booking.modules.event.repo;

import com.davendra.event_booking.modules.event.entity.ShowEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

public interface ShowRepository extends JpaRepository<ShowEntity, Long> {

        @Query("""
                        SELECT s FROM ShowEntity s
                        JOIN FETCH s.hall h
                        JOIN FETCH h.venue
                        WHERE s.id = :id
                        """)
        Optional<ShowEntity> findByIdWithHallAndVenue(@Param("id") Long id);

        @Query("""
                        SELECT COUNT(s) > 0 FROM ShowEntity s
                        WHERE s.hall.id = :hallId
                        AND s.startTime < :endTime
                        AND s.endTime > :startTime
                        AND (:excludeShowId IS NULL OR s.id <> :excludeShowId)
                        """)
        boolean existsOverlappingShow(
                        @Param("hallId") Long hallId,
                        @Param("startTime") LocalDateTime startTime,
                        @Param("endTime") LocalDateTime endTime,
                        @Param("excludeShowId") Long excludeShowId);

        @Query("""
                        SELECT s FROM ShowEntity s
                        WHERE s.event.id IN :eventIds
                        AND s.startTime >= :dayStart
                        AND s.startTime < :dayEnd
                        ORDER BY s.startTime ASC
                        """)
        List<ShowEntity> findByEventIdInAndShowDate(
                        @Param("eventIds") Collection<Long> eventIds,
                        @Param("dayStart") LocalDateTime dayStart,
                        @Param("dayEnd") LocalDateTime dayEnd);
}
