package org.example.smart_delivery.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.example.smart_delivery.entity.enums.UserRole;

@Getter
@Setter

@Entity
@Inheritance(strategy = InheritanceType.JOINED)
@Table(name = "users")
public class User extends AbstractAuditingEntity{
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
