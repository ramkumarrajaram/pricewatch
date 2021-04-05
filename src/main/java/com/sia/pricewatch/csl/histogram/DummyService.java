package com.sia.pricewatch.csl.histogram;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Value;


@Service
public class DummyService {

    private String dummyResponse;

    public DummyService(@Value("${dummy.data}") String dummyResponse) {
        this.dummyResponse = dummyResponse;
    }

    public CslHistogramResponse getHistoGram(CslHistogramRequest cslHistogramRequest) throws IOException {
        String response = getResponse();

        ObjectMapper mapper = new ObjectMapper();
        mapper.registerModule(new JavaTimeModule());

        return mapper.readValue(response, CslHistogramResponse.class);
    }

    private String getResponse() {
        return dummyResponse;
    }
}
