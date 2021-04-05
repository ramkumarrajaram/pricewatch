package com.sia.pricewatch.persistence.farehistogram;

import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;

public interface FareHistogramRepository extends JpaRepository<FareHistogramEntity, String> {

    List<FareHistogramEntity> findAllByOriginCodeAndDestinationCodeAndDepartureDateGreaterThanEqualAndDepartureDateLessThanEqual(String originAirportCode, String destinationAirportCode, LocalDate minusDays, LocalDate plusDays);
}
