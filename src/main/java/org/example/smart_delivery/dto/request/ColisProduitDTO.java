package org.example.smart_delivery.dto.request;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import java.math.BigDecimal;
import java.time.LocalDateTime;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ColisProduitDTO {
    private String id;

    @NotNull
    private String colisId;

    @Size(max = 50)
    @NotNull
    private String produitId;

    @Positive
    private int quantite;

    private LocalDateTime dateAjout;

    @DecimalMin(value = "0.0", inclusive = true)
    private BigDecimal prix;
}
