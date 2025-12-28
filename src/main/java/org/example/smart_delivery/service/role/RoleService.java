package org.example.smart_delivery.service.role;

import org.example.smart_delivery.dto.request.RoleReqDTO;
import org.example.smart_delivery.dto.response.RoleResDTO;

import java.util.List;

public interface RoleService {
    RoleResDTO create(RoleReqDTO dto);
    RoleResDTO update(String roleId,RoleReqDTO dto);
    RoleResDTO getById(String id);
    RoleResDTO getByName(String name);
    List<RoleResDTO> getAll();
    RoleResDTO assignPermissions(String roleId,List<String> permissionId);
    RoleResDTO takePermission(String roleId,List<String> permissionId);
    boolean delete(String id);
}
