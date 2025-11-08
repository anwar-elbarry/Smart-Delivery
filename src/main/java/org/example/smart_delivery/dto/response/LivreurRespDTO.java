package org.example.smart_delivery.dto.response;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
import org.example.smart_delivery.dto.request.UserDTO;
import org.example.smart_delivery.dto.request.ZoneDTO;

@Getter
@Setter
public class LivreurRespDTO {
    private String id;
    private UserDTO user;
    @NotBlank
    @Size(max = 100)
    private String vehicule;

    private ZoneDTO zoneAssignee;
}
