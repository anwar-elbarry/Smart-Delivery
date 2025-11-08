package org.example.smart_delivery.dto.response;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
import org.example.smart_delivery.dto.request.ColisDTO;
import org.example.smart_delivery.dto.request.ProduitDTO;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@Setter
public class ColisProduitRespDTO {
    private String id;

    private ColisDTO colis;

    private ProduitDTO produit;

    @Positive
    private int quantite;

    private LocalDateTime dateAjout;

    @DecimalMin(value = "0.0", inclusive = true)
    private BigDecimal prix;
}
