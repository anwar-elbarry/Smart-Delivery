package org.example.smart_delivery.service.permission;

import org.example.smart_delivery.dto.request.PermissionReqDTO;
import org.example.smart_delivery.dto.response.PermissionResDTO;

import java.util.List;

public interface PermissionService {
    PermissionResDTO create(PermissionReqDTO dto);
    PermissionResDTO take(String id, PermissionReqDTO dto);
    PermissionResDTO getById(String id);
    List<PermissionResDTO> getAll();
    void delete(String id);
}
