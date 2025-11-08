package org.example.smart_delivery.mapper.response;

import org.example.smart_delivery.dto.request.HistoriqueLivraisonDTO;
import org.example.smart_delivery.dto.response.HistoriqueLivraisonRespDTO;
import org.example.smart_delivery.entity.HistoriqueLivraison;
import org.example.smart_delivery.mapper.RefMapper;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring",uses = RefMapper.class)
public interface HistoLivrRespMapper {
    @Mapping(source = "colis",target = "colisId",qualifiedByName = "toColisId")
    HistoriqueLivraisonDTO toDto(HistoriqueLivraison entity);

    HistoriqueLivraison toEntity(HistoriqueLivraisonRespDTO dto);
}
