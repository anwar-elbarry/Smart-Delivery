package org.example.smart_delivery.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.List;


@Getter
@Setter

@Entity
@Table(name = "colis_produit")
public class ColisProduit {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;
    @Column(name = "produit_id")
    private String produitId;
    @Column(name = "colis_id")
    private String colisId;
    private String quantite;
    @Column(name = "date_ajout")
    private Timestamp dateAjout;
    private BigDecimal prix;

    @JoinColumn(name = "colis_id")
    @ManyToMany(fetch = FetchType.LAZY)
    List<Colis> colisList;
    @JoinColumn(name = "produit_id")
    @ManyToMany(fetch = FetchType.LAZY)
    List<Produit> produitList;
}
