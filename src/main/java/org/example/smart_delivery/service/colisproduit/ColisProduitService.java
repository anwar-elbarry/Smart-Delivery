package org.example.smart_delivery.service.colisproduit;

import org.example.smart_delivery.dto.ColisProduitDTO;
import java.util.List;

public interface ColisProduitService {
    ColisProduitDTO create(ColisProduitDTO dto);
    ColisProduitDTO update(String id, ColisProduitDTO dto);
    ColisProduitDTO getById(String id);
    List<ColisProduitDTO> getAll();
    void delete(String id);
}
