package org.example.smart_delivery.entity;

import jakarta.persistence.*;
import lombok.*;
import org.example.smart_delivery.entity.enums.UserRole;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter

@Entity
@Inheritance(strategy = InheritanceType.JOINED)
@Table(name = "users")
public class User extends AbstractAuditingEntity implements UserDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;
    private String username;
    private String nom;
    private String prenom;
    @Column(unique = true)
    private String email;
    private String telephone;
    private String adress;
    private String password;
    @ManyToOne
    @JoinColumn(name = "role_id")
    private Role role;

    private String verificationCode;
    private LocalDateTime verificationCodeExpired;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority(getRole().getName()));
    }

    @Override
    public String getPassword() {
        return this.password;
    }

    @Override
    public String getUsername() {
        return this.username;
    }

    @Override 
    public boolean isAccountNonExpired() { 
        return true; 
    }
    
    @Override 
    public boolean isAccountNonLocked() { 
        return true; 
    }
    
    @Override 
    public boolean isCredentialsNonExpired() { 
        return true; 
    }
    
    @Override 
    public boolean isEnabled() { 
        return true; 
    }
}
