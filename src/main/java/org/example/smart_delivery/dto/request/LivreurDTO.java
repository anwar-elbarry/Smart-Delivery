package org.example.smart_delivery.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LivreurDTO {
    private String id;
    @NotBlank
    private String userId;
    @NotBlank
    @Size(max = 100)
    private String vehicule;

    @Size(max = 50)
    private String zoneAssigneeId;
}
