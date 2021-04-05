package com.sia.pricewatch.details.web;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class FlightDetailsResponse {

    private String origin;
    private String dest;
    private List<Graph> graph;
    private List<Flight> flights;
    private List<Recommended> recommended;

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Graph {

        private String month;
        private String avgFare;
    }

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Flight {

        private LocalDate departure;
        private String price;
        private String flightno;
        private int availableSeats;
    }

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Recommended {

        private String destination;
        private String prize;
        private String cabinClass;
    }

}
