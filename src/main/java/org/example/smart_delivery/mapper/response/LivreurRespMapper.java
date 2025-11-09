package org.example.smart_delivery.mapper.response;

import org.example.smart_delivery.dto.request.LivreurDTO;
import org.example.smart_delivery.dto.response.LivreurRespDTO;
import org.example.smart_delivery.entity.Livreur;
import org.example.smart_delivery.mapper.RefMapper;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring",uses = RefMapper.class)
public interface LivreurRespMapper {
    @Mapping(source = "zoneAssigne", target = "zoneAssignee")
    @Mapping(source = "user", target = "user")
    LivreurRespDTO toRespDto(Livreur entity);

    @Mapping(source = "zoneAssignee", target = "zoneAssigne")
    @Mapping(source = "user", target = "user")
    @Mapping(target = "colisList", ignore = true)
    Livreur toRespEntity(LivreurRespDTO dto);
}
