package com.sia.pricewatch.csl.histogram;

import com.sia.pricewatch.PriceWatchException;
import com.sia.pricewatch.persistence.farehistogram.FareHistogramEntity;
import com.sia.pricewatch.persistence.farehistogram.FareHistogramRepository;
import com.sia.pricewatch.subscription.db.SubscribeUserEntity;
import feign.FeignException;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
@Slf4j
public class CslHistogramService {
    private CslHistogramClient client;
    private FareHistogramRepository repository;
    private DummyService dummyService;

    public void getHistogram(CslHistogramRequest cslHistogramRequest, List<SubscribeUserEntity> subscribeUserEntities) {

        try {

            //CslHistogramResponse response = client.getHistogram(cslHistogramRequest);

            CslHistogramResponse response = dummyService.getHistoGram(cslHistogramRequest);

            List<FareHistogramEntity> fareHistogramEntities = response.getFares()
                    .stream()
                    .map(fare -> FareHistogramEntity.builder()
                            .departureDate(fare.getDepartureDate())
                            .originCode(response.getOrigin().getAirportCode())
                            .destinationCode(response.getDestination().getAirportCode())
                            .price(fare.getTotalAmount())
                            .isNotified("N")
                            .build())
                    .collect(Collectors.toList());

            List<FareHistogramEntity> updatedFareHistogramEntities = new ArrayList<>();
            for (SubscribeUserEntity subscribeUserEntity : subscribeUserEntities) {
                LocalDate departureDate = subscribeUserEntity.getDepartureDate();
                LocalDate startDate = departureDate.minusDays(subscribeUserEntity.getDateRange());
                LocalDate endDate = departureDate.plusDays(subscribeUserEntity.getDateRange());
                for (FareHistogramEntity fareHistogramEntity : fareHistogramEntities) {
                    if (subscribeUserEntity.getOriginAirportCode()
                            .equalsIgnoreCase(fareHistogramEntity.getOriginCode()) &&
                            subscribeUserEntity.getDestinationAirportCode()
                                    .equalsIgnoreCase(fareHistogramEntity.getDestinationCode())
                            && (fareHistogramEntity.getDepartureDate().isAfter(startDate)
                            || fareHistogramEntity.getDepartureDate().isEqual(startDate)) &&
                            (fareHistogramEntity.getDepartureDate().isBefore(endDate)
                                    || fareHistogramEntity.getDepartureDate().isEqual(endDate))) {

                        fareHistogramEntity.setDeviceUid(subscribeUserEntity.getDeviceUid());

                        updatedFareHistogramEntities.add(fareHistogramEntity);
                    }
                }
            }

            repository.saveAll(updatedFareHistogramEntities);

            log.info("{}", response);
        } catch (FeignException | IOException ex) {
            log.error("An error occurred when connecting to csl service " + ex.getMessage());
        }
    }
}
