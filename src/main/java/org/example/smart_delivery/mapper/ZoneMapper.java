package org.example.smart_delivery.mapper;

import org.example.smart_delivery.dto.ZoneDTO;
import org.example.smart_delivery.entity.Zone;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ZoneMapper {
    ZoneDTO toDto(Zone entity);
    Zone toEntity(ZoneDTO dto);
}
