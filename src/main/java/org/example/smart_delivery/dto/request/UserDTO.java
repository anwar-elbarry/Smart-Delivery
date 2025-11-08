package org.example.smart_delivery.dto.request;

import jakarta.validation.constraints.*;
import lombok.Getter;
import lombok.Setter;
import org.example.smart_delivery.entity.enums.UserRole;

@Getter
@Setter
public class UserDTO {
    private String id;

    @NotBlank
    @Size(max = 50)
    private String nom;

    @NotBlank
    @Size(max = 50)
    private String prenom;

    @NotBlank
    @Email
    @Size(max = 100)
    private String email;

    @NotBlank
    @Size(max = 20)
    @Pattern(regexp = "^[+0-9().\\-\\s]{7,20}$", message = "telephone format is invalid")    private String telephone;

    @NotBlank
    @Size(max = 255)
    private String adress;

    @NotNull
    private UserRole role;
}
