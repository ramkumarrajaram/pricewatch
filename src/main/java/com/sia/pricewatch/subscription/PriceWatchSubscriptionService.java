package com.sia.pricewatch.subscription;

import com.sia.pricewatch.subscription.db.SubscribeUserEntity;
import com.sia.pricewatch.subscription.db.SubscribeUserRepository;
import com.sia.pricewatch.subscription.web.PriceWatchSubscribeResponse;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class PriceWatchSubscriptionService {

    private SubscribeUserRepository repository;

    public void subscribeToPriceWatch(PriceWatchSubscribeInput subscribeInput) {

        SubscribeUserEntity userEntity = SubscribeUserEntity.builder()
                .deviceUid(subscribeInput.getUid())
                .token(subscribeInput.getToken())
                .originAirportCode(subscribeInput.getSubscriptionDetails().getOriginAirportCode())
                .destinationAirportCode(subscribeInput.getSubscriptionDetails().getDestinationAirportCode())
                .departureDate(subscribeInput.getSubscriptionDetails().getDepartureDate())
                .dateRange(subscribeInput.getSubscriptionDetails().getDateRange())
                .price(subscribeInput.getSubscriptionDetails().getPrice())
                .cabinClass(subscribeInput.getSubscriptionDetails().getCabinClass())
                .createdDate(LocalDate.now())
                .frequency(subscribeInput.getSubscriptionDetails().getFrequency())
                .build();

        repository.save(userEntity);
    }

    public PriceWatchSubscribeResponse getDetails(String uid, boolean isNewUser) {

        List<SubscribeUserEntity> entities = repository.findByDeviceUid(uid);

        entities.sort(Comparator.comparing(SubscribeUserEntity::getCreatedDate).reversed());

        List<PriceWatchSubscribeResponse.Details> myList = entities.stream().map(entity -> getPriceWatchResponse(entity.getOriginAirportCode(), entity.getDestinationAirportCode(), entity.getCabinClass(), entity.getPrice().toString(), true))
                .collect(Collectors.toList());

        if(entities == null || entities.isEmpty()) {
            isNewUser = true;
        } else {
            isNewUser = false;
        }

        return PriceWatchSubscribeResponse.builder()
                .myList(myList)
                .seasonal(getSeasonalData())
                .suggested(getSuggestedData(isNewUser))
                .topPicks(getTopPicks())
                .build();
    }

    private List<PriceWatchSubscribeResponse.Details> getTopPicks() {
        return Arrays.asList(
                getPriceWatchResponse("SIN", "SYD", "Y", "649", false),
                getPriceWatchResponse("SIN", "LHR", "Y", "840", false),
                getPriceWatchResponse("SIN", "PEK", "Y", "540", false),
                getPriceWatchResponse("SIN", "SFO", "Y", "1040", false)
        );
    }

    private List<PriceWatchSubscribeResponse.Details> getSeasonalData() {
        return Arrays.asList(
                getPriceWatchResponse("SIN", "WUH", "Y", "640", false),
                getPriceWatchResponse("SIN", "MAA", "Y", "530", false),
                getPriceWatchResponse("SIN", "ICN", "Y", "770", false),
                getPriceWatchResponse("SIN", "KUL", "Y", "330", false)
        );
    }

    private List<PriceWatchSubscribeResponse.Details> getSuggestedData(boolean isNewUser) {
        if (isNewUser) {
            return Collections.singletonList(
                    getPriceWatchResponse("SIN", "HKT", "Y", "199", false));
        }
        return Arrays.asList(
                getPriceWatchResponse("SIN", "BPN", "Y", "220", false),
                getPriceWatchResponse("SIN", "HKT", "Y", "310", false),
                getPriceWatchResponse("SIN", "BKK", "Y", "270", false),
                getPriceWatchResponse("SIN", "MNL", "Y", "390", false)
        );
    }


    private PriceWatchSubscribeResponse.Details getPriceWatchResponse(
            String origin,
            String destination,
            String cabinClass,
            String price,
            boolean isAdded) {
        return PriceWatchSubscribeResponse.Details.builder()
                .origin(origin)
                .destination(destination)
                .cabinClass(getCabinClass(cabinClass))
                .price(price)
                .isAdded(isAdded)
                .imageUrl(getImageUrl(destination))
                .build();
    }

    private String getImageUrl(String destination) {
        List<String> urls = Arrays.asList(
                "https://www.singaporeair.com/saar5/images/fare-deal-images/Phuket.jpg",
                "https://www.singaporeair.com/saar5/images/fare-deal-images/Singapore.jpg",
                "https://www.singaporeair.com/saar5/images/fare-deal-images/Bali.jpg",
                "https://www.singaporeair.com/saar5/images/fare-deal-images/Munich.jpg",
                "https://www.singaporeair.com/saar5/images/fare-deal-images/DenpasarBali.jpg",
                "https://www.singaporeair.com/saar5/images/fare-deal-images/Dubai.jpg",
                "https://www.singaporeair.com/saar5/images/fare-deal-images/Tokyo.jpg",
                "https://www.singaporeair.com/saar5/images/fare-deal-images/Seoul.jpg",
                "https://www.singaporeair.com/saar5/images/fare-deal-images/SanFrancisco.jpg",
                "https://www.singaporeair.com/saar5/images/fare-deal-images/LoasAngeles.jpg",
                "https://www.singaporeair.com/saar5/images/fare-deal-images/Istanbul.jpg"
        );

        if (destination.equals("HKT")) {
            return urls.get(0);
        } else {
            int min = 0;
            int max = 10;
            Random random = new Random();
            return urls.get(random.nextInt(max + min) + min);
        }
    }

    private String getCabinClass(String cabinClass) {
        switch (cabinClass) {
            case "Y":
                return "Economy";
            case "S":
                return "Premium Economy";
            case "J":
                return "Business";
            case "F":
                return "First / Suites";
            default:
                return null;
        }
    }
}
