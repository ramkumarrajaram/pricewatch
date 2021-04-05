package com.sia.pricewatch.csl.histogram;

import com.github.rozidan.springboot.modelmapper.TypeMapConfigurer;
import com.sia.pricewatch.subscription.db.SubscribeUserEntity;
import org.modelmapper.TypeMap;
import org.springframework.stereotype.Component;

@Component
public class CslHistogramRequestMapperConfigurer
        extends TypeMapConfigurer<SubscribeUserEntity, CslHistogramRequest> {
    @Override
    public void configure(TypeMap<SubscribeUserEntity, CslHistogramRequest> typeMap) {
        typeMap.setConverter(mappingContext -> {
            SubscribeUserEntity userEntity = mappingContext.getSource();

            return CslHistogramRequest.builder()
                    .apiVersion(1)
                    .clientID("SAA")
                    .clientUUID(userEntity.getDeviceUid())
                    .request(getRequest(userEntity))
                    .build();
        });
    }

    private CslHistogramRequest.Request getRequest(SubscribeUserEntity userEntity) {
        return CslHistogramRequest.Request.builder()
                .adultCount(1)
                .childCount(0)
                .infantCount(0)
                .cabinClass(userEntity.getCabinClass())
                .dateRange(userEntity.getDateRange())
                .type("bydate")
                .itineraryDetails(getItineraryDetails(userEntity))
                .build();
    }

    private CslHistogramRequest.ItineraryDetails getItineraryDetails(SubscribeUserEntity userEntity) {
        return CslHistogramRequest.ItineraryDetails
                .builder()
                .departureDate(userEntity.getDepartureDate())
                .destinationAirportCode(userEntity.getDestinationAirportCode())
                .originAirportCode(userEntity.getOriginAirportCode())
                .build();
    }
}
