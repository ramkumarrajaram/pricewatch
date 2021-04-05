package com.sia.pricewatch;

import lombok.Data;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@Data
@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
public class PriceWatchException extends RuntimeException {
    private String code;

    public PriceWatchException(String code, String message) {
        super(message);
        this.code = code;
    }


    public PriceWatchException(String message) {
        super(message);
    }
}
