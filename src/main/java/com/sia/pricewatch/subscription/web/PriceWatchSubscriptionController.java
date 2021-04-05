package com.sia.pricewatch.subscription.web;

import com.sia.pricewatch.subscription.PriceWatchSubscriptionService;
import com.sia.pricewatch.subscription.PriceWatchSubscribeInput;
import com.sia.pricewatch.subscription.db.SubscribeUserEntity;
import com.sia.pricewatch.subscription.db.SubscribeUserRepository;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.CacheControl;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.util.List;

import static org.springframework.http.HttpHeaders.CACHE_CONTROL;

@RestController
@RequestMapping("/add")
@AllArgsConstructor
public class PriceWatchSubscriptionController {

    private PriceWatchSubscriptionService priceWatchSubscriptionService;
    private ModelMapper mapper;
    private SubscribeUserRepository repository;

    @PostMapping("/alert")
    public PriceWatchSubscribeResponse subscribe(
            HttpServletRequest servletRequest,
            HttpServletResponse response,
            @RequestBody PriceWatchSubscribeRequest subscribeRequest) {

        PriceWatchSubscribeInput subscribeInput = mapper
                .map(subscribeRequest, PriceWatchSubscribeInput.class);

        boolean isNewUser = false;

        List<SubscribeUserEntity> byDeviceUid = repository.findByDeviceUid(subscribeInput.getUid());

        if(byDeviceUid == null || byDeviceUid.isEmpty()) {
            isNewUser = true;
        }

        priceWatchSubscriptionService.subscribeToPriceWatch(subscribeInput);

        PriceWatchSubscribeResponse details = priceWatchSubscriptionService.getDetails(subscribeInput.getUid(),
                isNewUser);
        response.setHeader(
                CACHE_CONTROL,
                CacheControl.noStore().getHeaderValue()
        );

        return details;
    }
}
