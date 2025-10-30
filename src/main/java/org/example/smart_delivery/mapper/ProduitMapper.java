package org.example.smart_delivery.mapper;

import org.example.smart_delivery.dto.ProduitDTO;
import org.example.smart_delivery.entity.Produit;
import org.mapstruct.*;

@Mapper(componentModel = "spring")
public interface ProduitMapper {
    ProduitDTO toDto(Produit entity);
    @Mapping(target = "colisProduitList", ignore = true)
    Produit toEntity(ProduitDTO dto);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @Mapping(target = "colisProduitList", ignore = true)
    void updateEntityFromDto(ProduitDTO dto, @MappingTarget Produit entity);

}
