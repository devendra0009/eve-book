package com.davendra.event_booking.modules.inventory.service;

import com.davendra.event_booking.modules.event.entity.ShowEntity;
import com.davendra.event_booking.modules.inventory.entity.SeatEntity;
import com.davendra.event_booking.modules.inventory.enums.SeatType;
import com.davendra.event_booking.modules.venue.entity.VenueEntity;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDateTime;

@Service
public class ShowSeatPricingService {

    private static final BigDecimal BASE_PRICE = new BigDecimal("250");

    public BigDecimal calculatePrice(ShowEntity show, SeatEntity seat) {
        BigDecimal price = BASE_PRICE;
        price = price.add(seatTypeAdjustment(seat.getSeatType()));
        price = applyTimeAdjustment(price, show.getStartTime());
        price = applyVenueAdjustment(price, show.getHall().getVenue());
        return price.setScale(2, RoundingMode.HALF_UP);
    }

    private BigDecimal seatTypeAdjustment(SeatType seatType) {
        if (seatType == null) {
            return BigDecimal.ZERO;
        }
        return switch (seatType) {
            case PREMIUM -> new BigDecimal("100");
            case RECLINER -> new BigDecimal("150");
            case HANDICAPPED -> new BigDecimal("-50");
            default -> BigDecimal.ZERO;
        };
    }

    private BigDecimal applyTimeAdjustment(BigDecimal price, LocalDateTime startTime) {
        int hour = startTime.getHour();
        if (hour < 12) {
            return price.multiply(new BigDecimal("0.80"));
        }
        if (hour >= 18) {
            return price.multiply(new BigDecimal("1.30"));
        }
        return price;
    }

    private BigDecimal applyVenueAdjustment(BigDecimal price, VenueEntity venue) {
        if (venue == null) {
            return price;
        }
        if (venue.getCapacity() != null && venue.getCapacity() > 300) {
            return price.add(new BigDecimal("50"));
        }
        return price;
    }
}
