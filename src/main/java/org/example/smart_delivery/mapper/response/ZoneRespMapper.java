package org.example.smart_delivery.mapper.response;

import org.example.smart_delivery.dto.response.ZoneRespDTO;
import org.example.smart_delivery.entity.Zone;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ZoneRespMapper {
    ZoneRespDTO toRespDto(Zone entity);
    Zone toEntity(ZoneRespDTO dto);
}
