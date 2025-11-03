package org.example.smart_delivery.service.colis;

import org.example.smart_delivery.dto.ColisDTO;
import org.example.smart_delivery.entity.enums.ColisStatus;

import java.util.List;

public interface ColisService {
    ColisDTO create(ColisDTO dto);
    ColisDTO update(String id, ColisDTO dto);
    ColisDTO getById(String id);
    List<ColisDTO> getAll();
    void delete(String id);
    void Assign_col(String colisId, String livreurId);

    List<ColisDTO>filterByStatus(ColisStatus status);
}
