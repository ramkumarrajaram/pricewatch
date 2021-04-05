package com.sia.pricewatch.pricewatchbatch;

import com.sia.pricewatch.subscription.db.SubscribeUserEntity;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class NotificationService {

    private NotificationSendService notificationSendService;

    public void processEntities(List<SubscribeUserEntity> entities) {
        entities.forEach(entity -> {
            notificationSendService.validateAndSendNotification(entity);
        });

    }
}
