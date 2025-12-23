package org.example.smart_delivery.service.role;

import lombok.RequiredArgsConstructor;
import org.example.smart_delivery.dto.request.RoleReqDTO;
import org.example.smart_delivery.dto.response.RoleResDTO;
import org.example.smart_delivery.entity.Permission;
import org.example.smart_delivery.entity.Role;
import org.example.smart_delivery.exception.ResourceNotFoundException;
import org.example.smart_delivery.mapper.RoleMapper;
import org.example.smart_delivery.repository.PermissionRepository;
import org.example.smart_delivery.repository.RoleRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class RoleServiceImpl implements RoleService{

    private final RoleRepository roleRepository;
    private final PermissionRepository permissionRepository;
    private final RoleMapper roleMapper;

    @Override
    public RoleResDTO create(RoleReqDTO dto) {
        Role role = roleMapper.toEntity(dto);
        Role savedRole = roleRepository.save(role);
        return roleMapper.toResDTO(savedRole);
    }

    @Override
    public RoleResDTO update(String roleId, RoleReqDTO dto) {
        Role role = roleRepository.findById(roleId)
                .orElseThrow(() -> new ResourceNotFoundException("Role", roleId));
        role.setName(dto.getRoleName());
        Role updatedRole = roleRepository.save(role);
        return roleMapper.toResDTO(updatedRole);
    }

    @Override
    public RoleResDTO getById(String id) {
        Role role = roleRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Role", id));
        return roleMapper.toResDTO(role);
    }

    @Override
    public List<RoleResDTO> getAll() {
        return roleRepository.findAll().stream()
                .map(roleMapper::toResDTO)
                .collect(Collectors.toList());
    }

    @Override
    public boolean delete(String id) {
        if (!roleRepository.existsById(id)) {
            throw new ResourceNotFoundException("Role", id);
        }
        roleRepository.deleteById(id);
        return true;
    }

    @Override
    public RoleResDTO assign(String roleId, String permissionId) {
        Role role = roleRepository.findById(roleId)
                .orElseThrow(() -> new ResourceNotFoundException("Role", roleId));

        Permission permission = permissionRepository.findById(permissionId)
                .orElseThrow(() -> new ResourceNotFoundException("Permission", permissionId));

        role.getPermissions().add(permission);
        Role updatedRole = roleRepository.save(role);
        return roleMapper.toResDTO(updatedRole);
    }
}
