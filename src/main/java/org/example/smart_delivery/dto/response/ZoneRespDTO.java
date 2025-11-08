package org.example.smart_delivery.dto.response;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ZoneRespDTO {
    private String id;

    @NotBlank
    @Size(max = 50)
    private String nome;
    private Integer codePostal;
}
