package org.example.smart_delivery.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.example.smart_delivery.entity.enums.ColisStatus;
import org.example.smart_delivery.entity.enums.Priority;

import java.math.BigDecimal;


@Getter
@Setter

@Entity
@Table(name = "colis")
public class Colis {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;
    private String description;
    private BigDecimal poids;
    private ColisStatus statut;
    private Priority priorite;

    @Column(name = "livreur_id")
    private String livreurId;
    @Column(name = "client_expediteur_id")
    private String clientExpediteurId;
    @Column(name = "destinataire_id")
    private String destinataireId;
}
