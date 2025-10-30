package org.example.smart_delivery.service.zone;

import org.example.smart_delivery.dto.ZoneDTO;
import java.util.List;

public interface ZoneService {
    ZoneDTO create(ZoneDTO dto);
    ZoneDTO update(String id, ZoneDTO dto);
    ZoneDTO getById(String id);
    List<ZoneDTO> getAll();
    void delete(String id);
}
