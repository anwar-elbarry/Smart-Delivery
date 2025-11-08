package org.example.smart_delivery.mapper.response;

import org.example.smart_delivery.dto.request.ColisProduitDTO;
import org.example.smart_delivery.dto.response.ColisProduitRespDTO;
import org.example.smart_delivery.entity.ColisProduit;
import org.example.smart_delivery.mapper.RefMapper;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", uses = RefMapper.class)
public interface ColisProduitRespMapper {
    @Mapping(source = "colis",target = "colisId",qualifiedByName = "toColisId")
    @Mapping(source = "produit",target = "produitId",qualifiedByName = "toProduitId")
    ColisProduitDTO toDto(ColisProduit entity);
    ColisProduit toEntity(ColisProduitRespDTO dto);
}
