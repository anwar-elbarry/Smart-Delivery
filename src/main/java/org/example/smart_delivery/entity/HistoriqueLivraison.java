package org.example.smart_delivery.entity;

import jakarta.persistence.*;
import lombok.*;
import org.example.smart_delivery.entity.enums.ColisStatus;

import java.time.LocalDateTime;

@Builder
@NoArgsConstructor
@AllArgsConstructor
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
    private Colis colis;

    private ColisStatus statut;
    @Column(name = "date_changement")
    private LocalDateTime dateChangement;
    private String commentaire;
}
