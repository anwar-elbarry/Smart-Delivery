package org.example.smart_delivery.dto.response;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.PositiveOrZero;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class ProduitRespDTO {
    private String id;

    @NotBlank
    @Size(max = 50)
    private String nom;

    @NotBlank
    @Size(max = 50)
    private String categorie;

    @PositiveOrZero
    private double poids;

    @DecimalMin(value = "0.0", inclusive = true)
    private BigDecimal prix;
}
