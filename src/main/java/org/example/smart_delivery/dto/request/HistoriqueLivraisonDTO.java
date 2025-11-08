package org.example.smart_delivery.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
import org.example.smart_delivery.entity.enums.ColisStatus;

import java.time.LocalDateTime;

@Getter
@Setter
public class HistoriqueLivraisonDTO {
    private String id;

    @NotBlank
    private String colisId;

    private ColisStatus statut;
    private LocalDateTime dateChangement;
    @Size(max = 255)
    private String commentaire;
}
