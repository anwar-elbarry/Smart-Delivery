package org.example.smart_delivery.mapper.request;

import org.example.smart_delivery.dto.request.LivreurDTO;
import org.example.smart_delivery.entity.Livreur;
import org.example.smart_delivery.mapper.RefMapper;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring",uses = RefMapper.class)
public interface LivreurMapper {
    @Mapping(source = "zoneAssigne", target = "zoneAssigneeId", qualifiedByName = "toIdZone")
    @Mapping(source = "user", target = "userId", qualifiedByName = "toId")
    LivreurDTO toDto(Livreur entity);

    @Mapping(source = "zoneAssigneeId", target = "zoneAssigne", qualifiedByName = "toZoneRef")
    @Mapping(source = "userId", target = "user", qualifiedByName = "toUserRef")
    @Mapping(target = "colisList", ignore = true)
    Livreur toEntity(LivreurDTO dto);
}
