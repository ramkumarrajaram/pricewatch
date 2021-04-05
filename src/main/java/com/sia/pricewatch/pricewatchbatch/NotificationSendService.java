package com.sia.pricewatch.pricewatchbatch;

import com.sia.pricewatch.persistence.farehistogram.FareHistogramEntity;
import com.sia.pricewatch.persistence.farehistogram.FareHistogramRepository;
import com.sia.pricewatch.pricewatchbatch.notification.PushNotificationClient;
import com.sia.pricewatch.pricewatchbatch.notification.fcm.PushNotificationRequest;
import com.sia.pricewatch.subscription.db.SubscribeUserEntity;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class NotificationSendService {

    private FareHistogramRepository repository;
    private PushNotificationClient pushNotificationClient;

    public void validateAndSendNotification(SubscribeUserEntity entity) {

        LocalDate departureDate = entity.getDepartureDate();
        int dateRange = entity.getDateRange();
        String originAirportCode = entity.getOriginAirportCode();
        String destinationAirportCode = entity.getDestinationAirportCode();

        List<FareHistogramEntity> fareHistogramEntities = repository
                .findAllByOriginCodeAndDestinationCodeAndDepartureDateGreaterThanEqualAndDepartureDateLessThanEqual(
                        originAirportCode,
                        destinationAirportCode,
                        departureDate.minusDays(dateRange),
                        departureDate.plusDays(dateRange)
                );

        List<FareHistogramEntity> filteredEntities = fareHistogramEntities.stream()
                .filter(fareHistogramEntity -> ((fareHistogramEntity.getPrice().compareTo(entity.getPrice()) < 0)
                        && fareHistogramEntity.getIsNotified().equals("N")))
                .collect(Collectors.toList());

        if (filteredEntities != null && !filteredEntities.isEmpty()) {
            try {
                PushNotificationRequest notificationRequest = PushNotificationRequest.builder()
                        .to(entity.getToken())
                        .notification(PushNotificationRequest.Notification.builder()
                                .body("Hi User, result matched for your subscribed price alert. Grab them quickly before it expires!!!")
                                .title("Price alert")
                                .build())
                        .build();

                pushNotificationClient.pushNotification(
                        "key=AAAApFtt5HY:APA91bEzh5Fc5Zuxt3MU0a8xDP_KgR8AvQPyyvNwn2WXUhkcaRCyBz-jpVngOZxlOQBQkdlIYZncFNx-mTwFYmaixCqTE_m2nlIXe4nVsHDpsWnqWsseAqSjm7-CFy0tKb0aUPGzZUWI",
                        notificationRequest);

            } catch (Exception ex) {
                System.out.println(ex.getMessage());
            }

            List<FareHistogramEntity> collect = filteredEntities.stream()
                    .peek(filteredEntity -> filteredEntity.setIsNotified("Y")).collect(Collectors.toList());

            repository.saveAll(collect);
        }
    }
}
