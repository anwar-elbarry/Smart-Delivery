package org.example.smart_delivery.mapper;

import org.example.smart_delivery.dto.request.PermissionReqDTO;
import org.example.smart_delivery.dto.response.PermissionResDTO;
import org.example.smart_delivery.entity.Permission;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface PermissionMapper {
    @Mapping(target = "id", ignore = true)
    Permission toEntity(PermissionReqDTO reqDTO);
    PermissionResDTO toResp(Permission entity);
}
