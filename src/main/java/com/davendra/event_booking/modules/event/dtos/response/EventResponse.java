package com.davendra.event_booking.modules.event.dtos.response;

import com.davendra.event_booking.modules.event.enums.EventType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class EventResponse {

    private Long id;
    private String title;
    private String description;
    private Integer duration;
    private String language;
    private String genre;
    private String organizedBy;
    private EventType eventType;
    private String posterUrl;
    private String bannerUrl;
    private String ageRating;
    private Boolean bookingEnabled;
    private Boolean active;
    private String tags;
    private Long venueId;
    private LocalDateTime createdAt;
}
