package com.sia.pricewatch.persistence.farehistogram;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDate;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class FareHistogramId implements Serializable {

    private String originCode;
    private String destinationCode;
    private LocalDate departureDate;
}
