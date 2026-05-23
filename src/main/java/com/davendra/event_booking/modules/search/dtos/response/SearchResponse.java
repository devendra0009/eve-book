package com.davendra.event_booking.modules.search.dtos.response;

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
public class SearchResponse {

    private Long eventId;
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
    private LocalDateTime createdAt;

    private Long venueId;
    private String venueName;
    private String venueCity;
    private String venueState;
    private String venueCountry;
    private String location;

    private Long showId;
    private LocalDateTime showStartTime;
    private LocalDateTime showEndTime;
}
