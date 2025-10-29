package org.example.smart_delivery.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter

@Entity
@Table(name = "livreur")
public class Livreur extends User{

    private String vehicule;
    @OneToOne
    private Zone zoneAssigne;
    
    @OneToMany(mappedBy = "livreur")
    private List<Colis> colisList ;
}
