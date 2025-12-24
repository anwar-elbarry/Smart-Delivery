package org.example.smart_delivery.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RoleReqDTO {
    @NotBlank
    private String roleName;
}
