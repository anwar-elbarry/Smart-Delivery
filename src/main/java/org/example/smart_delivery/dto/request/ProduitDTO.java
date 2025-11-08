package org.example.smart_delivery.dto.request;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import jakarta.validation.constraints.PositiveOrZero;
import java.math.BigDecimal;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProduitDTO {
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
