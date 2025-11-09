package org.example.smart_delivery.mapper.response;

import org.example.smart_delivery.dto.response.ProduitRespDTO;
import org.example.smart_delivery.entity.Produit;
import org.mapstruct.*;

@Mapper(componentModel = "spring")
public interface ProduitRespMapper {
    ProduitRespDTO toRespDto(Produit entity);
    @Mapping(target = "colisProduitList", ignore = true)
    Produit toRespEntity(ProduitRespDTO dto);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @Mapping(target = "colisProduitList", ignore = true)
    void updateEntityFromDto(ProduitRespDTO dto, @MappingTarget Produit entity);

}
