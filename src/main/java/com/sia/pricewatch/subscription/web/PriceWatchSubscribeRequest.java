package com.sia.pricewatch.subscription.web;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PriceWatchSubscribeRequest {

    private String uid;
    private String token;
    private SubscriptionDetails subscriptionDetails;

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class SubscriptionDetails {

        private String originAirportCode;
        private String destinationAirportCode;
        private LocalDate departureDate;
        private LocalDate returnDate;
        private int dateRange;
        private BigDecimal price;
        private String cabinClass;
        private int frequency;
    }
}
