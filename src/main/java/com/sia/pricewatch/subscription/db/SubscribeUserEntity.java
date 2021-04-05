package com.sia.pricewatch.subscription.db;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
@Table(name = "subscribe_user")
public class SubscribeUserEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String deviceUid;

    private String token;
    private String originAirportCode;
    private String destinationAirportCode;
    private LocalDate departureDate;
    private int dateRange;
    private BigDecimal price;
    private String cabinClass;
    private LocalDate createdDate;
    private int frequency;
}
