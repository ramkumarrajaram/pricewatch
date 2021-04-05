package com.sia.pricewatch.pricewatchbatch;

import com.sia.pricewatch.subscription.db.SubscribeUserEntity;
import com.sia.pricewatch.subscription.db.SubscribeUserRepository;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Date;
import java.util.List;

@Component
@AllArgsConstructor
public class PushNotificationScheduledTasks {

    SubscribeUserRepository repository;
    ModelMapper mapper;

    private static final Logger log = LoggerFactory.getLogger(PushNotificationScheduledTasks.class);

    private static final SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");
    private HistogramService cslService;
    private NotificationService notificationService;

    //@Scheduled(cron = "0 0 0/1 * * ?")
    @Scheduled(fixedRate = 5000)
    public void scheduleCslFareService() {
        List<SubscribeUserEntity> entities = repository
                .findByDepartureDateAfter(LocalDate.now().minusDays(1));

        notificationService.processEntities(entities);

        log.info("The time is now {}", dateFormat.format(new Date()));
        //log.info("Entities data {}", entities.get(0).getDeviceUid());
    }
}