package com.davendra.event_booking.modules.event.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "venues")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class VenueEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * PVR Phoenix Mall
     */
    @Column(nullable = false)
    private String name;

    private String city;

    private String state;

    private Double latitude;

    private Double longitude;

    /**
     * Total venue capacity
     */
    private Integer capacity;

    private String address;

    private String pincode;

    private Boolean active = true;
}