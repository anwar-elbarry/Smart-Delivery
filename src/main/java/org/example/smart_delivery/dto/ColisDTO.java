package org.example.smart_delivery.dto;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.math.BigDecimal;
import lombok.Getter;
import lombok.Setter;
import org.example.smart_delivery.entity.enums.ColisStatus;
import org.example.smart_delivery.entity.enums.Priority;

@Getter
@Setter
public class ColisDTO {
    private String id;

    @Size(max = 50)
    private String description;

    @DecimalMin(value = "0.0", inclusive = true)
    private BigDecimal poids;

    @NotNull
    private ColisStatus statut;

    @NotNull
    private Priority priorite;

    @Size(max = 50)
    private String livreurId;

    @Size(max = 50)
    private String clientExpediteurId;

    @Size(max = 50)
    private String destinataireId;
}
