package org.example.smart_delivery.mapper.response;

import org.example.smart_delivery.dto.response.HistoriqueLivraisonRespDTO;
import org.example.smart_delivery.entity.HistoriqueLivraison;
import org.example.smart_delivery.mapper.RefMapper;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring",uses = RefMapper.class)
public interface HistoLivrRespMapper {
    HistoriqueLivraisonRespDTO toRespDto(HistoriqueLivraison entity);

    HistoriqueLivraison toRespEntity(HistoriqueLivraisonRespDTO dto);
}
