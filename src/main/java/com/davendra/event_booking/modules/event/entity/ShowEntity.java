package com.davendra.event_booking.modules.event.entity;

import com.davendra.event_booking.modules.event.enums.ShowStatus;
import com.davendra.event_booking.modules.venue.entity.HallEntity;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "shows")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ShowEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * Actual show start
     */
    @Column(nullable = false)
    private LocalDateTime startTime;

    /**
     * Actual show end
     */
    @Column(nullable = false)
    private LocalDateTime endTime;

    @Enumerated(EnumType.STRING)
    private ShowStatus status;

    /**
     * Event being played
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "event_id", nullable = false)
    private EventEntity event;

    /**
     * Hall where event is running
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "hall_id", nullable = false)
    private HallEntity hall;

    /**
     * Whether booking is open
     */
    private Boolean bookingOpen = true;

    /**
     * Total available seats cache
     */
    private Integer availableSeats;

    private LocalDateTime createdAt;
}