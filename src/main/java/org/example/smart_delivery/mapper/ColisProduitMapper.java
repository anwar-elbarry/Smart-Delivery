package org.example.smart_delivery.mapper;

import org.example.smart_delivery.dto.ColisProduitDTO;
import org.example.smart_delivery.entity.ColisProduit;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", uses = RefMapper.class)
public interface ColisProduitMapper {
    @Mapping(source = "colis", target = "colisId",qualifiedByName = "toColisId")
    @Mapping(source = "produit", target = "produitId",qualifiedByName = "toProduitId")
    ColisProduitDTO toDto(ColisProduit entity);

    @Mapping(source = "colisId", target = "colis", qualifiedByName = "toColisRef")
    @Mapping(source = "produitId", target = "produit", qualifiedByName = "toProduitRef")
    ColisProduit toEntity(ColisProduitDTO dto);
}
