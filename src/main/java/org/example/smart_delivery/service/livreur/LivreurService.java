package org.example.smart_delivery.service.livreur;

import org.example.smart_delivery.dto.LivreurDTO;
import java.util.List;

public interface LivreurService {
    LivreurDTO create(LivreurDTO dto);
    LivreurDTO update(String id, LivreurDTO dto);
    LivreurDTO getById(String id);
    List<LivreurDTO> getAll();
    void delete(String id);
}
