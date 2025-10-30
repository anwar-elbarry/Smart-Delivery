package org.example.smart_delivery.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ZoneDTO {
    private String id;

    @NotBlank
    @Size(max = 50)
    private String nom;
    private Integer codePostal;
}
