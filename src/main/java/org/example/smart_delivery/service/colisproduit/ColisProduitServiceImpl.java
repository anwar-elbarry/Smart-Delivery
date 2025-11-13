package org.example.smart_delivery.service.colisproduit;

import org.example.smart_delivery.dto.request.ColisProduitDTO;
import org.example.smart_delivery.entity.ColisProduit;
import org.example.smart_delivery.mapper.request.ColisProduitMapper;
import org.example.smart_delivery.repository.ColisProduitRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ColisProduitServiceImpl implements ColisProduitService {
    private final ColisProduitRepository colisProduitRepository;
    private final ColisProduitMapper colisProduitMapper;

    @Override
    public ColisProduitDTO create(ColisProduitDTO dto) {
        ColisProduit entity = colisProduitMapper.toEntity(dto);
        ColisProduit saved = colisProduitRepository.save(entity);
        return colisProduitMapper.toDto(saved);
    }

    @Override
    public ColisProduitDTO update(String id, ColisProduitDTO dto) {
        if (!colisProduitRepository.existsById(id)) {
            throw new IllegalArgumentException("ColisProduit not found: " + id);
        }
        ColisProduit entity = colisProduitMapper.toEntity(dto);
        entity.setId(id);
        ColisProduit saved = colisProduitRepository.save(entity);
        return colisProduitMapper.toDto(saved);
    }

    @Override
    public ColisProduitDTO getById(String id) {
        ColisProduit entity = colisProduitRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("ColisProduit not found: " + id));
        return colisProduitMapper.toDto(entity);
    }

    @Override
    public List<ColisProduitDTO> getAll() {
        return colisProduitRepository.findAll()
                .stream()
                .map(colisProduitMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public void delete(String id) {
        if (!colisProduitRepository.existsById(id)) {
            throw new IllegalArgumentException("ColisProduit not found: " + id);
        }
        colisProduitRepository.deleteById(id);
    }
}
