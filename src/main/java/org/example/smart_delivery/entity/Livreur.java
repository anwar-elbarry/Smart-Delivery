package org.example.smart_delivery.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter

@Entity
@Table(name = "livreurs")
public class Livreur{
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    @OneToOne
    private User user;

    private String vehicule;

    @OneToOne
    @JoinColumn(name = "zone_assignee_id")
    private Zone zoneAssigne;
    
    @OneToMany(mappedBy = "livreur")
    private List<Colis> colisList ;
}
