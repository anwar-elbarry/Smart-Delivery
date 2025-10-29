package org.example.smart_delivery.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.sql.Timestamp;


@Getter
@Setter

@Entity
@Table(name = "colis_produit")
public class ColisProduit {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    @ManyToOne
    @JoinColumn(name = "produit_id")
    private Produit produitId;

    @ManyToOne
    @JoinColumn(name = "colis_id")
    private Colis colisId;

    private int quantite;
    @Column(name = "date_ajout")
    private Timestamp dateAjout;

    private BigDecimal prix;
}
