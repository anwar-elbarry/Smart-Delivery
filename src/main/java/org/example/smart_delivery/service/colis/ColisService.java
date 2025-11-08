package org.example.smart_delivery.service.colis;

import org.example.smart_delivery.dto.request.ColisDTO;
import org.example.smart_delivery.entity.enums.ColisStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface ColisService {
    ColisDTO create(ColisDTO dto);
    ColisDTO update(String id, ColisDTO dto);
    ColisDTO getById(String id);
    Page<ColisDTO> getAll(Pageable pageable);
    void delete(String id);
    void Assign_col(String colisId, String livreurId);

    Page<ColisDTO>filterByStatus(ColisStatus status,Pageable pageable);
    void createColisRequest(String expedId , String distenId,List<String> produitIds);
}
