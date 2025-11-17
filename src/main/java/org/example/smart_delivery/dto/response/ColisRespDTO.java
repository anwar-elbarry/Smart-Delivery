package org.example.smart_delivery.dto.response;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;
import org.example.smart_delivery.dto.request.LivreurDTO;
import org.example.smart_delivery.dto.request.UserDTO;
import org.example.smart_delivery.dto.request.ZoneDTO;
import org.example.smart_delivery.entity.Zone;
import org.example.smart_delivery.entity.enums.ColisStatus;
import org.example.smart_delivery.entity.enums.Priority;
import org.springframework.lang.Nullable;

import java.math.BigDecimal;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class ColisRespDTO {
    private String id;

    @Size(max = 255)
    private String description;

    @DecimalMin(value = "0.0", inclusive = true)
    private BigDecimal poids;

    private ColisStatus statut;

    @NotNull
    private Priority priorite;

    private String villeDestination;

    private ZoneDTO zone;

    private LivreurDTO livreur;

    private UserDTO clientExpediteur;

    private UserDTO destinataire;
}
