package com.sia.pricewatch.details;

import com.sia.pricewatch.details.web.FlightDetailsResponse;
import com.sia.pricewatch.persistence.farehistogram.FareHistogramEntity;
import com.sia.pricewatch.persistence.farehistogram.FareHistogramRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class FlightDetailsService {

    private FareHistogramRepository repository;

    public FlightDetailsResponse getDetails(String origin, String destination) {

        List<FareHistogramEntity> entities = repository
                .findAllByOriginCodeAndDestinationCodeAndDepartureDateGreaterThanEqualAndDepartureDateLessThanEqual(
                        origin,
                        destination,
                        LocalDate.now().minusDays(30),
                        LocalDate.now().plusDays(30)
                );

        return FlightDetailsResponse.builder()
                .origin(origin)
                .dest(destination)
                .graph(getGraphDetails())
                .flights(buildFlights(entities))
                .recommended(getRecommended())
                .build();
    }

    private List<FlightDetailsResponse.Recommended> getRecommended() {
        return Arrays.asList(
                FlightDetailsResponse.Recommended.builder()
                        .cabinClass("Economy")
                        .destination("HKT")
                        .prize("170")
                        .build(),
                FlightDetailsResponse.Recommended.builder()
                        .cabinClass("Economy")
                        .destination("HKT")
                        .prize("170")
                        .build(),
                FlightDetailsResponse.Recommended.builder()
                        .cabinClass("Economy")
                        .destination("HKT")
                        .prize("170")
                        .build()
        );
    }

    private List<FlightDetailsResponse.Flight> buildFlights(List<FareHistogramEntity> entities) {
        return entities.stream()
                .map(entity -> FlightDetailsResponse.Flight
                        .builder()
                        .departure(entity.getDepartureDate())
                        .price(entity.getPrice().toString())
                        .availableSeats(getRandomInt())
                        .flightno("SQ " + getRandomInt())
                        .build())
                .collect(Collectors.toList());
    }

    private int getRandomInt() {
        int min = 1;
        int max = 10;
        Random random = new Random();
        return random.nextInt(max + min) + min;
    }

    private List<FlightDetailsResponse.Graph> getGraphDetails() {
        return Arrays.asList(
                getFlightDetailsResponse("jan", "200"),
                getFlightDetailsResponse("feb", "350"),
                getFlightDetailsResponse("march", "210"),
                getFlightDetailsResponse("april", "220"),
                getFlightDetailsResponse("may", "200"),
                getFlightDetailsResponse("june", "190")
        );
    }

    private FlightDetailsResponse.Graph getFlightDetailsResponse(String month, String price) {
        return FlightDetailsResponse.Graph.builder()
                .month(month)
                .avgFare(price)
                .build();
    }
}
