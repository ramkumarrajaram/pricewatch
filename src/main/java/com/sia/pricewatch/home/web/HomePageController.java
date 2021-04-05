package com.sia.pricewatch.home.web;

import com.sia.pricewatch.subscription.PriceWatchSubscriptionService;
import com.sia.pricewatch.subscription.db.SubscribeUserEntity;
import com.sia.pricewatch.subscription.db.SubscribeUserRepository;
import com.sia.pricewatch.subscription.web.PriceWatchSubscribeResponse;
import lombok.AllArgsConstructor;
import org.springframework.http.CacheControl;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;

import java.util.List;

import static org.springframework.http.HttpHeaders.CACHE_CONTROL;

@RestController
@AllArgsConstructor
public class HomePageController {

    private SubscribeUserRepository repository;
    private PriceWatchSubscriptionService priceWatchSubscriptionService;

    @GetMapping("/home/{uid}")
    public PriceWatchSubscribeResponse getHomeDetails(
            @PathVariable("uid") String uid,
            HttpServletResponse response) {


        List<SubscribeUserEntity> byDeviceUid = repository.findByDeviceUid(uid);
        boolean isNewUser = false;

        if (byDeviceUid == null || byDeviceUid.isEmpty()) {
            isNewUser = true;
        }

        response.setHeader(
                CACHE_CONTROL,
                CacheControl.noStore().getHeaderValue()
        );

        return priceWatchSubscriptionService.getDetails(uid, isNewUser);

    }
}
