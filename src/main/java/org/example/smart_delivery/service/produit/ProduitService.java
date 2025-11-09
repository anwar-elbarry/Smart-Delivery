package org.example.smart_delivery.service.produit;

import org.example.smart_delivery.dto.request.ProduitDTO;
import org.example.smart_delivery.dto.response.ProduitRespDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ProduitService {
    ProduitRespDTO create(ProduitDTO dto);
    ProduitRespDTO update(String id, ProduitDTO dto);
    ProduitRespDTO getById(String id);
    Page<ProduitRespDTO> getAll(Pageable pageable);
    void delete(String id);
}
