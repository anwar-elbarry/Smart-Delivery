package org.example.smart_delivery.service.produit;

import org.example.smart_delivery.dto.request.ProduitDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ProduitService {
    ProduitDTO create(ProduitDTO dto);
    ProduitDTO update(String id, ProduitDTO dto);
    ProduitDTO getById(String id);
    Page<ProduitDTO> getAll(Pageable pageable);
    void delete(String id);
}
