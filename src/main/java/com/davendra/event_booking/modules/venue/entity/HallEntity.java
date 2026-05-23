package com.davendra.event_booking.modules.venue.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "halls")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class HallEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * Audi 1
     */
    @Column(nullable = false)
    private String name;

    /**
     * Total seats in hall
     */
    private Integer totalSeating;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "venue_id", nullable = false)
    private VenueEntity venue;

    /**
     * Dolby / IMAX / 3D etc
     */
    private String screenType;

    private Boolean active = true;
}