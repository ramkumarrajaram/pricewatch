package com.sia.pricewatch.subscription.web;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PriceWatchSubscribeResponse {

    private List<Details> myList;
    private List<Details> suggested;
    private List<Details> seasonal;
    private List<Details> topPicks;

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Details {
        private String origin;
        private String destination;
        private String price;
        private String cabinClass; //Economy, Premium Economy, Business, First / Suites
        @JsonProperty("isAdded")
        private boolean isAdded;
        private String imageUrl;
    }
}
