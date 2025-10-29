package org.example.smart_delivery.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.example.smart_delivery.entity.enums.ColisStatus;
import org.example.smart_delivery.entity.enums.Priority;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;


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

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "livreur_id")
    private Livreur livreur;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "client_expediteur_id")
    private User clientExpediteurId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "destinataire_id")
    private User destinataireId;

    @OneToMany(mappedBy = "colis",orphanRemoval = true , cascade = CascadeType.ALL,fetch = FetchType.LAZY)
    List<ColisProduit> colisProduitList = new ArrayList<>();
}
