package org.example.smart_delivery.service.zone;

import org.example.smart_delivery.dto.request.ZoneDTO;
import org.example.smart_delivery.dto.response.ZoneRespDTO;

import java.util.List;

public interface ZoneService {
    ZoneRespDTO create(ZoneDTO dto);
    ZoneRespDTO update(String id, ZoneDTO dto);
    ZoneRespDTO getById(String id);
    List<ZoneRespDTO> getAll();
    void delete(String id);
}
