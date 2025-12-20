package org.example.smart_delivery.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.UniqueElements;
@Getter
@Setter
public class PermissionReqDTO {
    @UniqueElements
    @NotBlank
    private String name;
}
