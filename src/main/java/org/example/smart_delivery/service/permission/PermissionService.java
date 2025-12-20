package org.example.smart_delivery.service.permission;

import org.example.smart_delivery.dto.request.PermissionReqDTO;
import org.example.smart_delivery.dto.response.PermissionResDTO;
import org.example.smart_delivery.entity.enums.UserRole;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface PermissionService {
    PermissionResDTO create(PermissionReqDTO dto);
    PermissionResDTO take(String id, PermissionReqDTO dto);
    PermissionResDTO getById(String id);
    boolean assign(UserRole role,String permissionId);
    Page<PermissionResDTO> getAll(Pageable pageable);
    void delete(String id);
}
