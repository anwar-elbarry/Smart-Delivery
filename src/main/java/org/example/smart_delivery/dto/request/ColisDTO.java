package org.example.smart_delivery.dto.request;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.math.BigDecimal;
import lombok.Getter;
import lombok.Setter;
import org.example.smart_delivery.entity.enums.ColisStatus;
import org.example.smart_delivery.entity.enums.Priority;
import org.springframework.lang.Nullable;

@Getter
@Setter
public class ColisDTO {
    private String id;

    @Size(max = 255)
    private String description;

    @DecimalMin(value = "0.0", inclusive = true)
    private BigDecimal poids;

    private ColisStatus statut;

    @NotNull
    private Priority priorite;

    @Size(max = 50)
    @Nullable
    private String livreurId;

    @Size(max = 50)
    private String clientExpediteurId;

    @Size(max = 50)
    @Nullable
    private String destinataireId;
}
