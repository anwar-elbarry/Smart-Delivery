package org.example.smart_delivery.mapper;

import org.example.smart_delivery.dto.request.RoleReqDTO;
import org.example.smart_delivery.dto.response.RoleResDTO;
import org.example.smart_delivery.entity.Role;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring",uses = PermissionMapper.class)
public interface RoleMapper {
    @Mapping(source = "name", target = "roleName")
    RoleResDTO toResDTO(Role entity);

    @Mapping(target = "permissions", ignore = true)
    @Mapping(target = "id", ignore = true)
    @Mapping(source = "roleName", target = "name")
    Role toEntity(RoleReqDTO dto);
}
