package org.example.smart_delivery.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.example.smart_delivery.entity.enums.ColisStatus;

@Getter
@Setter

@Entity
@Table(name = "historique_livraison")
public class HistoriqueLivraison {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;
    @Column(name = "colis_id")
    private String colisId;
    private ColisStatus statut;
    @Column(name = "date_changement")
    private String dateChangement;
    private String commentaire;
}
