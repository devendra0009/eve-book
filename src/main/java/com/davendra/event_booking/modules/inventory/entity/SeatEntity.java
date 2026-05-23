package com.davendra.event_booking.modules.inventory.entity;

import com.davendra.event_booking.modules.inventory.enums.SeatStatus;
import com.davendra.event_booking.modules.inventory.enums.SeatType;
import com.davendra.event_booking.modules.venue.entity.HallEntity;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;

@Entity
@Table(name = "seats")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SeatEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * A1, B4 etc
     */
    @Column(nullable = false)
    private String seatNumber;

    /**
     * A, B, C
     */
    private String rowNumber;

    /**
     * 1,2,3,4
     */
    private Integer columnNumber;

    @Enumerated(EnumType.STRING)
    private SeatType seatType;

    @Enumerated(EnumType.STRING)
    private SeatStatus status; // means a seat can be permanently booked, if seat is broken so temporarily under maintenance

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "hall_id", nullable = false)
    private HallEntity hall;

    @CreationTimestamp
    private LocalDateTime createdAt;

    @UpdateTimestamp
    private LocalDateTime updatedAt;
}