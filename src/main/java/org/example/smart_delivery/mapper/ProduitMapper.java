package org.example.smart_delivery.mapper;

import org.example.smart_delivery.dto.ProduitDTO;
import org.example.smart_delivery.entity.Produit;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ProduitMapper {
    ProduitDTO toDto(Produit entity);
    Produit toEntity(ProduitDTO dto);

}
