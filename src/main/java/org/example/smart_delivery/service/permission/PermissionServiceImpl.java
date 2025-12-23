package org.example.smart_delivery.service.permission;

import lombok.RequiredArgsConstructor;
import org.example.smart_delivery.dto.request.PermissionReqDTO;
import org.example.smart_delivery.dto.response.PermissionResDTO;
import org.example.smart_delivery.entity.Permission;
import org.example.smart_delivery.entity.Role;
import org.example.smart_delivery.entity.enums.UserRole;
import org.example.smart_delivery.exception.ResourceNotFoundException;
import org.example.smart_delivery.mapper.PermissionMapper;
import org.example.smart_delivery.repository.PermissionRepository;
import org.example.smart_delivery.repository.RoleRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class PermissionServiceImpl implements PermissionService {

    private final PermissionRepository permissionRepository;
    private final PermissionMapper permissionMapper;
    private final RoleRepository roleRepository;

    @Override
    public PermissionResDTO create(PermissionReqDTO dto) {
        Permission permission = permissionMapper.toEntity(dto);
        Permission savedPermission = permissionRepository.save(permission);
        return permissionMapper.toResp(savedPermission);
    }

    @Override
    public PermissionResDTO take(String id, PermissionReqDTO dto) {
        Permission permission = permissionRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Permission", id));
        permission.setName(dto.getName());
        Permission updatedPermission = permissionRepository.save(permission);
        return permissionMapper.toResp(updatedPermission);
    }

    @Override
    public PermissionResDTO getById(String id) {
        Permission permission = permissionRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Permission" , id));
        return permissionMapper.toResp(permission);
    }


    @Override
    public List<PermissionResDTO> getAll() {
        return permissionRepository.findAll().stream()
                .map(permissionMapper::toResp).toList();
    }

    @Override
    public void delete(String id) {
        if (!permissionRepository.existsById(id)) {
            throw new ResourceNotFoundException("Permission", id);
        }
        permissionRepository.deleteById(id);
    }
}
