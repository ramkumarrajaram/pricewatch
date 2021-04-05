package com.sia.pricewatch.csl.histogram;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "viewhistogram", url = "http://svdev.spark.sq.com.sg:9100/api/flighthistogram/")
public interface CslHistogramClient {

    @PostMapping(path = "/viewhistogram")
    CslHistogramResponse getHistogram(@RequestBody CslHistogramRequest request);
}
