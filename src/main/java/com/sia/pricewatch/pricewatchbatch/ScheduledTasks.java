package com.sia.pricewatch.pricewatchbatch;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Date;
import java.util.List;

import com.sia.pricewatch.csl.histogram.CslHistogramRequest;
import com.sia.pricewatch.subscription.db.SubscribeUserEntity;
import com.sia.pricewatch.subscription.db.SubscribeUserRepository;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class ScheduledTasks {

    SubscribeUserRepository repository;
    ModelMapper mapper;

    private static final Logger log = LoggerFactory.getLogger(ScheduledTasks.class);

    private static final SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");
    private HistogramService cslService;

    //@Scheduled(cron = "0 0 0/1 * * ?")
    @Scheduled(fixedRate = 5000*60)
    public void scheduleCslFareService() {
        List<SubscribeUserEntity> entities = repository
                .findByDepartureDateAfter(LocalDate.now().minusDays(1));

        List<CslHistogramRequest> cslHistogramRequests = mapper
                .map(entities, new TypeToken<List<CslHistogramRequest>>() {}.getType());

        cslService.process(cslHistogramRequests, entities);

        log.info("The time is now {}", dateFormat.format(new Date()));
        //log.info("Entities data {}", entities.get(0).getDeviceUid());
    }
}