package org.example.smart_delivery.dto.response;

import lombok.*;
import org.example.smart_delivery.entity.enums.Provider;
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

    private RoleResDTO role;
    private Provider provider;
    private String providerId;
    private Boolean enable;
}
