package org.example.smart_delivery.service.colis;

import org.example.smart_delivery.dto.ColisDTO;
import org.example.smart_delivery.entity.Colis;
import org.example.smart_delivery.mapper.ColisMapper;
import org.example.smart_delivery.repository.ColisRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ColisServiceImpl implements ColisService {
    private final ColisRepository colisRepository;
    private final ColisMapper colisMapper;

    @Override
    public ColisDTO create(ColisDTO dto) {
        Colis entity = colisMapper.toEntity(dto);
        Colis saved = colisRepository.save(entity);
        return colisMapper.toDto(saved);
    }

    @Override
    public ColisDTO update(String id, ColisDTO dto) {
        if (!colisRepository.existsById(id)) {
            throw new IllegalArgumentException("Colis not found: " + id);
        }
        Colis entity = colisMapper.toEntity(dto);
        entity.setId(id);
        Colis saved = colisRepository.save(entity);
        return colisMapper.toDto(saved);
    }

    @Override
    public ColisDTO getById(String id) {
        Colis entity = colisRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Colis not found: " + id));
        return colisMapper.toDto(entity);
    }

    @Override
    public List<ColisDTO> getAll() {
        return colisRepository.findAll()
                .stream()
                .map(colisMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public void delete(String id) {
        if (!colisRepository.existsById(id)) {
            throw new IllegalArgumentException("Colis not found: " + id);
        }
        colisRepository.deleteById(id);
    }
}
