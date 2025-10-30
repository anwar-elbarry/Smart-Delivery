package org.example.smart_delivery.mapper;

import org.example.smart_delivery.dto.LivreurDTO;
import org.example.smart_delivery.entity.Livreur;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring",uses = RefMapper.class)
public interface LivreurMapper {
    @Mapping(source = "zoneAssigne", target = "zoneAssigneeId", qualifiedByName = "toIdZone")
    LivreurDTO toDto(Livreur entity);

    @Mapping(source = "zoneAssigneeId", target = "zoneAssigne", qualifiedByName = "toZoneRef")
    Livreur toEntity(LivreurDTO dto);
}
