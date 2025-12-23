package org.example.smart_delivery.dto.response;

import jakarta.validation.constraints.*;
import lombok.*;
import org.example.smart_delivery.entity.enums.UserRole;
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class UserRespDTO {
    private String id;

    private String nom;

    private String prenom;

    private String username;

    private String email;

    private String telephone;

    private String adress;

    private String roleName;
}
