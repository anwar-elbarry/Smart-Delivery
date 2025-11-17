package org.example.smart_delivery.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Builder
@AllArgsConstructor
@NoArgsConstructor
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
