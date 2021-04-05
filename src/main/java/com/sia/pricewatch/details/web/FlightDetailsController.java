package com.sia.pricewatch.details.web;


import com.sia.pricewatch.details.FlightDetailsService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;

@RestController
@AllArgsConstructor
public class FlightDetailsController {

    private FlightDetailsService flightDetailsService;

    @GetMapping("/details/{origin}/{destination}")
    public FlightDetailsResponse getFlightDetails(
            @PathVariable("origin") String origin,
            @PathVariable("destination") String destination,
            HttpServletResponse response) {

        return flightDetailsService.getDetails(origin, destination);
    }
}
