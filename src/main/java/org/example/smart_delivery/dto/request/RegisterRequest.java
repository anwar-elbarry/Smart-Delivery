package org.example.smart_delivery.dto.request;

import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
public record RegisterRequest(
        @NotBlank String username,
        @NotBlank @Email String email,
        @NotBlank String password,
        @NotBlank String nom,
        @NotBlank String prenom,
        @NotBlank String telephone,
        @NotBlank String adresse,
        @NotBlank @Enumerated(EnumType.STRING) String role
) {
}
