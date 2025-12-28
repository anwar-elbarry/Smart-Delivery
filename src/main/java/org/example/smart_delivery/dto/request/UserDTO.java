package org.example.smart_delivery.dto.request;

import jakarta.annotation.Nullable;
import jakarta.validation.constraints.*;
import lombok.*;
import org.example.smart_delivery.entity.enums.Provider;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class UserDTO {
    private String id;

    @Size(max = 50)
    private String nom;

    @Size(max = 25)
    private String username;

    @Size(min = 8,message = "at least 8 digits")
    private String password;

    @Size(max = 50)
    private String prenom;

    @Email
    @Size(max = 100)
    private String email;

    @Size(max = 20)
    @Pattern(regexp = "^[+0-9().\\-\\s]{7,20}$", message = "telephone format is invalid")    private String telephone;

    @Size(max = 255)
    private String adress;

    @NotNull
    private String roleId;

    @NotNull
    private Provider provider;
    @NotNull
    private String providerId;
    @NotNull
    private Boolean enable;
}
