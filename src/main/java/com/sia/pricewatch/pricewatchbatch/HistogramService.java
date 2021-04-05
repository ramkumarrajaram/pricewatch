package com.sia.pricewatch.pricewatchbatch;

import com.sia.pricewatch.csl.histogram.CslHistogramRequest;
import com.sia.pricewatch.csl.histogram.CslHistogramService;
import com.sia.pricewatch.subscription.db.SubscribeUserEntity;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class HistogramService {

    private CslHistogramService cslHistogramService;

    public void process(List<CslHistogramRequest> cslHistogramRequests, List<SubscribeUserEntity> entities) {

        cslHistogramRequests.forEach(cslHistogramRequest -> {
            cslHistogramService.getHistogram(cslHistogramRequest, entities);
        });
    }
}
