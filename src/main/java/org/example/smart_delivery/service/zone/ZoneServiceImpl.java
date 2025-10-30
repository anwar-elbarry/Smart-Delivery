package org.example.smart_delivery.service.zone;

import lombok.RequiredArgsConstructor;
import org.example.smart_delivery.dto.ZoneDTO;
import org.example.smart_delivery.entity.Zone;
import org.example.smart_delivery.mapper.ZoneMapper;
import org.example.smart_delivery.repository.ZoneRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ZoneServiceImpl implements ZoneService {

    private final ZoneRepository zoneRepository;
    private final ZoneMapper zoneMapper;
    @Override
    @Transactional
    public ZoneDTO create(ZoneDTO dto) {
        Zone entity = zoneMapper.toEntity(dto);
        entity.setId(null);
        Zone saved = zoneRepository.save(entity);
        return zoneMapper.toDto(saved);
    }

    @Override
    @Transactional
    public ZoneDTO update(String id, ZoneDTO dto) {
        Zone entity = zoneRepository.findById(id).orElseThrow(()-> new IllegalArgumentException("Zone not found: " + id));
        zoneMapper.updateEntityFromDto(dto,entity);
        Zone saved = zoneRepository.save(entity);
        return zoneMapper.toDto(saved);
    }

    @Override
    @Transactional(readOnly = true)
    public ZoneDTO getById(String id) {
        Zone zone = zoneRepository.findById(id).orElseThrow(()-> new IllegalArgumentException("Zone not found: " + id));
        return  zoneMapper.toDto(zone);
    }

    @Override
    @Transactional(readOnly = true)
    public List<ZoneDTO> getAll() {
        List<Zone> zoneList = zoneRepository.findAll();
        return zoneList.stream().map(zoneMapper::toDto).toList();
    }

    @Override
    @Transactional
    public void delete(String id) {
        Zone entity = zoneRepository.findById(id).orElseThrow(()-> new IllegalArgumentException("Zone not found: " + id));
        zoneRepository.delete(entity);
    }
}
