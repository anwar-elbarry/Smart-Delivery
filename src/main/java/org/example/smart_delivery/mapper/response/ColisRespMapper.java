package org.example.smart_delivery.mapper.response;

import org.example.smart_delivery.dto.response.ColisRespDTO;
import org.example.smart_delivery.entity.Colis;
import org.example.smart_delivery.mapper.RefMapper;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper(componentModel = "spring",uses = RefMapper.class,nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface ColisRespMapper {
    ColisRespDTO toRespDto(Colis entity);

    @Mapping(target = "colisProduitList", ignore = true)
    Colis toRespEntity(ColisRespDTO dto);
}