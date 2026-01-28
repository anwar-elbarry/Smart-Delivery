package org.example.smart_delivery.dto.response;

import lombok.Getter;
import lombok.Setter;

import java.util.Set;

@Getter
@Setter
public class RoleResDTO {
    private String id;
    private String roleName;
    private Set<PermissionResDTO> permissions;
}
