package org.example.smart_delivery.mapper.response;

import org.example.smart_delivery.dto.response.ZoneRespDTO;
import org.example.smart_delivery.entity.Zone;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper(componentModel = "spring")
public interface ZoneRespMapper {
    ZoneRespDTO toRespDto(Zone entity);
    Zone toEntity(ZoneRespDTO dto);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateRespEntityFromDto(ZoneRespDTO dto, @MappingTarget Zone entity);
}
