package com.sia.pricewatch.csl.histogram;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CslHistogramResponse {

    private Airport origin;
    private Airport destination;
    private Currency currency;
    private int avgTripDuration;
    private List<Fare> fares;

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Airport {
        private String airportCode;
        private String airportName;
        private String cityCode;
        private String cityName;
        private String countryCode;
        private String countryName;
    }

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Currency {

        private String code;
        private String name;
        private int precision;
    }

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Fare {

        @JsonDeserialize(using = LocalDateDeserializer.class)
        @JsonSerialize(using = LocalDateSerializer.class)
        private LocalDate departureDate;
        private LocalDate returnDate;
        private BigDecimal fare;
        private BigDecimal tax;
        private BigDecimal totalAmount;
    }

}
