package org.example.smart_delivery.service.colis;

import lombok.*;

import java.math.BigDecimal;

@Builder
@NoArgsConstructor
@Getter
@Setter
public class Coliscounter {
    private Long count;
    private BigDecimal poids;

    public Coliscounter(Long count, BigDecimal poids) {
        this.count = count == null ? 0L : count;
        this.poids = poids == null ? BigDecimal.ZERO : poids;
    }


}
