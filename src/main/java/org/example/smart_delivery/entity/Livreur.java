package org.example.smart_delivery.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter

@Entity
@Table(name = "livreur")
public class Livreur extends User{

    private String vehicule;
    @OneToOne
    private Zone zoneAssigne;
}
