package org.example.smart_delivery.mapper;

import org.example.smart_delivery.dto.request.ColisDTO;
import org.example.smart_delivery.entity.Colis;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring",uses = RefMapper.class)
public interface ColisMapper {
    @Mapping(source = "livreur", target = "livreurId", qualifiedByName = "toIdLivreur")
    @Mapping(source = "clientExpediteur", target = "clientExpediteurId", qualifiedByName = "toId")
    @Mapping(source = "destinataire", target = "destinataireId", qualifiedByName = "toId")
    ColisDTO toDto(Colis entity);

    @Mapping(source = "livreurId", target = "livreur", qualifiedByName = "toLivreurRef")
    @Mapping(source = "clientExpediteurId", target = "clientExpediteur", qualifiedByName = "toUserRef")
    @Mapping(source = "destinataireId", target = "destinataire", qualifiedByName = "toUserRef")
    @Mapping(target = "colisProduitList", ignore = true)
    Colis toEntity(ColisDTO dto);
}