package com.davendra.event_booking.modules.event.entity;

import com.davendra.event_booking.modules.event.enums.EventType;
import com.davendra.event_booking.modules.venue.entity.VenueEntity;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "events")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class EventEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * Movie / Concert / Show title
     */
    @Column(nullable = false)
    private String title;

    @Column(columnDefinition = "TEXT")
    private String description;

    /**
     * Duration in minutes
     */
    private Integer duration;

    /**
     * English, Hindi, etc
     */
    private String language;

    /**
     * Action, Comedy, Thriller, etc
     */
    private String genre;

    /**
     * Organizer / production house
     */
    private String organizedBy;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private EventType eventType;

    /**
     * Poster / thumbnail
     */
    private String posterUrl;

    /**
     * Optional banner image
     */
    private String bannerUrl;

    /**
     * PG13 / U / A etc
     */
    private String ageRating;

    /**
     * Whether booking is enabled
     */
    private Boolean bookingEnabled = true;

    /**
     * Whether event is currently active
     */
    private Boolean active = true;

    /**
     * Search optimization
     */
    private String tags;

    @Column(nullable = false)
    private LocalDateTime createdAt;

    /**
     * Default venue
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "venue_id")
    private VenueEntity venue;
}