package org.example.smart_delivery.mapper.response;

import org.example.smart_delivery.dto.response.ColisProduitRespDTO;
import org.example.smart_delivery.entity.ColisProduit;
import org.example.smart_delivery.mapper.RefMapper;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring", uses = RefMapper.class)
public interface ColisProduitRespMapper {

    ColisProduitRespDTO toRespDto(ColisProduit entity);
    ColisProduit toRespEntity(ColisProduitRespDTO dto);
}
