package org.example.smart_delivery.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter

@Entity
@Table(name = "produit")
public class Produit {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;
    private String nom;
    private String categorie;
    private String poids;
    private String prix;
}
