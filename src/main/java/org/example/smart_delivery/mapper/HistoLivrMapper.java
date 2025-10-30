package org.example.smart_delivery.mapper;

import org.example.smart_delivery.dto.HistoriqueLivraisonDTO;
import org.example.smart_delivery.entity.HistoriqueLivraison;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring",uses = RefMapper.class)
public interface HistoLivrMapper {
    @Mapping(source = "colis",target = "colisId",qualifiedByName = "toColisId")
    HistoriqueLivraisonDTO toDto(HistoriqueLivraison entity);

    @Mapping(source = "colisId",target = "colis",qualifiedByName = "toColisRef")
    HistoriqueLivraison toEntity(HistoriqueLivraisonDTO dto);
}
