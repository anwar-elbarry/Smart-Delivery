package org.example.smart_delivery.service.zone;

import lombok.RequiredArgsConstructor;
import org.example.smart_delivery.dto.request.ZoneDTO;
import org.example.smart_delivery.dto.response.ZoneRespDTO;
import org.example.smart_delivery.entity.Zone;
import org.example.smart_delivery.mapper.request.ZoneMapper;
import org.example.smart_delivery.mapper.response.ZoneRespMapper;
import org.example.smart_delivery.repository.ZoneRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ZoneServiceImpl implements ZoneService {

    private final ZoneRepository zoneRepository;
    private final ZoneMapper zoneMapper;
    private final ZoneRespMapper zoneRespMapper;
    @Override
    @Transactional
    public ZoneRespDTO create(ZoneDTO dto) {
        Zone entity = zoneMapper.toEntity(dto);
        entity.setId(null);
        Zone saved = zoneRepository.save(entity);
        return zoneRespMapper.toRespDto(saved);
    }

    @Override
    @Transactional
    public ZoneRespDTO update(String id, ZoneDTO dto) {
        Zone entity = zoneRepository.findById(id).orElseThrow(()-> new IllegalArgumentException("Zone not found: " + id));
        zoneMapper.updateEntityFromDto(dto,entity);
        Zone saved = zoneRepository.save(entity);
        return zoneRespMapper.toRespDto(saved);
    }

    @Override
    @Transactional(readOnly = true)
    public ZoneRespDTO getById(String id) {
        Zone zone = zoneRepository.findById(id).orElseThrow(()-> new IllegalArgumentException("Zone not found: " + id));
        return  zoneRespMapper.toRespDto(zone);
    }

    @Override
    @Transactional(readOnly = true)
    public List<ZoneRespDTO> getAll() {
        List<Zone> zoneList = zoneRepository.findAll();
        return zoneList.stream().map(zoneRespMapper::toRespDto).toList();
    }

    @Override
    @Transactional
    public void delete(String id) {
        Zone entity = zoneRepository.findById(id).orElseThrow(()-> new IllegalArgumentException("Zone not found: " + id));
        zoneRepository.delete(entity);
    }
}
