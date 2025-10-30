package org.example.smart_delivery.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LivreurDTO extends UserDTO {
    @NotBlank
    @Size(max = 100)
    private String vehicule;

    @Size(max = 50)
    private String zoneAssigneeId;
}
