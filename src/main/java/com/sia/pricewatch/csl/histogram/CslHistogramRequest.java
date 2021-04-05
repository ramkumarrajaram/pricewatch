package com.sia.pricewatch.csl.histogram;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CslHistogramRequest {

    private String clientUUID;
    private String clientID;
    private int apiVersion;
    private Request request;

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Request {

        private String cabinClass;
        private int dateRange;
        private int adultCount;
        private int childCount;
        private int infantCount;
        private String type;

        private ItineraryDetails itineraryDetails;
    }

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class ItineraryDetails {

        private String originAirportCode;
        private String destinationAirportCode;
        private LocalDate departureDate;
    }
}
