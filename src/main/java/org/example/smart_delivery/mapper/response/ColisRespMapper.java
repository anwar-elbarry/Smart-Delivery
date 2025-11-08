package org.example.smart_delivery.mapper.response;

import org.example.smart_delivery.dto.request.ColisDTO;
import org.example.smart_delivery.dto.response.ColisRespDTO;
import org.example.smart_delivery.entity.Colis;
import org.example.smart_delivery.mapper.RefMapper;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring",uses = RefMapper.class)
public interface ColisRespMapper {
    @Mapping(source = "livreur", target = "livreurId", qualifiedByName = "toIdLivreur")
    @Mapping(source = "clientExpediteur", target = "clientExpediteurId", qualifiedByName = "toId")
    @Mapping(source = "destinataire", target = "destinataireId", qualifiedByName = "toId")
    ColisDTO toDto(Colis entity);

    @Mapping(target = "colisProduitList", ignore = true)
    Colis toEntity(ColisRespDTO dto);
}