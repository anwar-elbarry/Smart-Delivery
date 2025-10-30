package org.example.smart_delivery.service.produit;

import org.example.smart_delivery.dto.ProduitDTO;
import java.util.List;

public interface ProduitService {
    ProduitDTO create(ProduitDTO dto);
    ProduitDTO update(String id, ProduitDTO dto);
    ProduitDTO getById(String id);
    List<ProduitDTO> getAll();
    void delete(String id);
}
