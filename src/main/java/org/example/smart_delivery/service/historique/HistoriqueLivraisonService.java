package org.example.smart_delivery.service.historique;

import org.example.smart_delivery.dto.request.HistoriqueLivraisonDTO;
import org.example.smart_delivery.dto.response.HistoriqueLivraisonRespDTO;

import java.util.List;

public interface HistoriqueLivraisonService {
    HistoriqueLivraisonRespDTO create(HistoriqueLivraisonDTO dto);
    HistoriqueLivraisonRespDTO update(String id, HistoriqueLivraisonDTO dto);
    HistoriqueLivraisonRespDTO getById(String id);
    List<HistoriqueLivraisonRespDTO> getAll();
    void delete(String id);
}
