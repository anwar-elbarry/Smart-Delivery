package org.example.smart_delivery.entity;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter

@Entity
@Table(name = "produits")
public class Produit {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;
    private String nom;
    private String categorie;
    private double poids;
    private BigDecimal prix;

    @OneToMany(mappedBy = "produit",orphanRemoval = true , cascade = CascadeType.ALL,fetch = FetchType.LAZY)
    List<ColisProduit> colisProduitList = new ArrayList<>();
}
