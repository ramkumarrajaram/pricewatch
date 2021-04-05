package com.sia.pricewatch.persistence.farehistogram;

import lombok.*;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Table(name = "fare_histogram")
@IdClass(FareHistogramId.class)
public class FareHistogramEntity {

    @Id
    private String originCode;
    @Id
    private String destinationCode;
    @Id
    private LocalDate departureDate;

    private BigDecimal price;

    private String isNotified;

    private String deviceUid;
}