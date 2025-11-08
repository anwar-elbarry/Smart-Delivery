package org.example.smart_delivery.mapper;

import org.example.smart_delivery.dto.request.ZoneDTO;
import org.example.smart_delivery.entity.Zone;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper(componentModel = "spring")
public interface ZoneMapper {
    ZoneDTO toDto(Zone entity);
    Zone toEntity(ZoneDTO dto);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateEntityFromDto(ZoneDTO dto, @MappingTarget Zone entity);
}
