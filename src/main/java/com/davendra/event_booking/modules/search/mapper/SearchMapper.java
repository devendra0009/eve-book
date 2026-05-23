package com.davendra.event_booking.modules.search.mapper;

import com.davendra.event_booking.modules.event.entity.EventEntity;
import com.davendra.event_booking.modules.event.entity.ShowEntity;
import com.davendra.event_booking.modules.search.dtos.response.SearchResponse;
import com.davendra.event_booking.modules.venue.entity.VenueEntity;
import org.springframework.stereotype.Component;

@Component
public class SearchMapper {

    public SearchResponse toResponse(EventEntity event, ShowEntity show) {
        VenueEntity venue = event.getVenue();

        SearchResponse.SearchResponseBuilder builder = SearchResponse.builder()
                .eventId(event.getId())
                .title(event.getTitle())
                .description(event.getDescription())
                .duration(event.getDuration())
                .language(event.getLanguage())
                .genre(event.getGenre())
                .organizedBy(event.getOrganizedBy())
                .eventType(event.getEventType())
                .posterUrl(event.getPosterUrl())
                .bannerUrl(event.getBannerUrl())
                .ageRating(event.getAgeRating())
                .bookingEnabled(event.getBookingEnabled())
                .active(event.getActive())
                .tags(event.getTags())
                .createdAt(event.getCreatedAt());

        if (venue != null) {
            builder.venueId(venue.getId())
                    .venueName(venue.getName())
                    .venueCity(venue.getCity())
                    .venueState(venue.getState())
                    .venueCountry(venue.getCountry())
                    .location(formatLocation(venue));
        }

        if (show != null) {
            builder.showId(show.getId())
                    .showStartTime(show.getStartTime())
                    .showEndTime(show.getEndTime());
        }

        return builder.build();
    }

    private static String formatLocation(VenueEntity venue) {
        String city = venue.getCity();
        String state = venue.getState();
        String country = venue.getCountry();

        if (city != null && state != null && country != null) {
            return city + ", " + state + ", " + country;
        }
        if (city != null && state != null) {
            return city + ", " + state;
        }
        if (city != null) {
            return city;
        }
        return venue.getName();
    }
}
