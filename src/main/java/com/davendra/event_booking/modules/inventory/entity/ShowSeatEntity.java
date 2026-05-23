package com.davendra.event_booking.modules.inventory.entity;

import com.davendra.event_booking.modules.event.entity.ShowEntity;
import com.davendra.event_booking.modules.inventory.enums.ShowSeatStatus;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(
        name = "show_seats",
        uniqueConstraints = {
                @UniqueConstraint(
                        name = "uk_show_seat",
                        columnNames = {"show_id", "seat_id"}
                )
        }
)
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ShowSeatEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * Show reference
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "show_id", nullable = false)
    private ShowEntity show;

    /**
     * Seat reference
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "seat_id", nullable = false)
    private SeatEntity seat;

    /**
     * Dynamic pricing per show
     */
    @Column(nullable = false)
    private BigDecimal price;

    /**
     * Current seat status for this show
     */
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private ShowSeatStatus status;

    /**
     * User who temporarily locked seat
     * Firebase UID / User ID
     */
    private String lockedByUser;

    /**
     * Seat reservation expiry time
     * Example:
     * now + 10 minutes
     */
    private LocalDateTime lockedUntil;

    /**
     * When lock released
     */
    private LocalDateTime releasedAt;

    /**
     * Optimistic locking
     */
    @Version
    private Long version;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;

    /**
     * Audit hooks
     */
    @PrePersist
    public void prePersist() {

        this.createdAt = LocalDateTime.now();
        this.updatedAt = LocalDateTime.now();

        if (this.status == null) {
            this.status = ShowSeatStatus.AVAILABLE;
        }
    }

    @PreUpdate
    public void preUpdate() {
        this.updatedAt = LocalDateTime.now();
    }
}