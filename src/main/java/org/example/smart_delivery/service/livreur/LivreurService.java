package org.example.smart_delivery.service.livreur;

import org.example.smart_delivery.dto.request.LivreurDTO;
import org.example.smart_delivery.dto.response.LivreurRespDTO;

import java.util.List;

public interface LivreurService {
    LivreurRespDTO create(LivreurDTO dto);
    LivreurRespDTO update(String id, LivreurDTO dto);
    LivreurRespDTO getById(String id);
    List<LivreurRespDTO> getAll();
    void delete(String id);
}
