package org.example.smart_delivery.entity;

import jakarta.persistence.*;
import lombok.*;
import org.example.smart_delivery.entity.enums.UserRole;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter

@Entity
@Inheritance(strategy = InheritanceType.JOINED)
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;
    private String nom;
    private String prenom;
    @Column(unique = true)
    private String email;
    private String telephone;
    private String adress;
    @Enumerated(EnumType.STRING)
    private UserRole role;
}
