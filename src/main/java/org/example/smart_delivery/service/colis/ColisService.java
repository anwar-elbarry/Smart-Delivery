package org.example.smart_delivery.service.colis;

import org.example.smart_delivery.dto.request.ColisDTO;
import org.example.smart_delivery.dto.response.ColisRespDTO;
import org.example.smart_delivery.entity.enums.ColisStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface ColisService {
    ColisRespDTO create(ColisDTO dto);
    ColisRespDTO update(String id, ColisDTO dto);
    ColisRespDTO getById(String id);
    Page<ColisRespDTO> getAll(Pageable pageable);
    void delete(String id);
    void Assign_col(String colisId, String livreurId);

    Page<ColisRespDTO>filterByStatus(ColisStatus status,Pageable pageable);
    void createColisRequest(String expedId , String distenId,List<String> produitIds);
}
