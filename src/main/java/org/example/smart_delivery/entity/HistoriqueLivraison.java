package org.example.smart_delivery.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.example.smart_delivery.entity.enums.ColisStatus;

import java.time.LocalDateTime;

@Getter
@Setter

@Entity
@Table(name = "historique_livraison")
public class HistoriqueLivraison {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    @ManyToOne
    @JoinColumn(name = "colis_id")
    private Colis colisId;

    private ColisStatus statut;
    @Column(name = "date_changement")
    private LocalDateTime dateChangement;
    private String commentaire;
}
