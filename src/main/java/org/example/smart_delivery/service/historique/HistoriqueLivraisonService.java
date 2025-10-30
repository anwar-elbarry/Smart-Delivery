package org.example.smart_delivery.service.historique;

import org.example.smart_delivery.dto.HistoriqueLivraisonDTO;
import java.util.List;

public interface HistoriqueLivraisonService {
    HistoriqueLivraisonDTO create(HistoriqueLivraisonDTO dto);
    HistoriqueLivraisonDTO update(String id, HistoriqueLivraisonDTO dto);
    HistoriqueLivraisonDTO getById(String id);
    List<HistoriqueLivraisonDTO> getAll();
    void delete(String id);
}
