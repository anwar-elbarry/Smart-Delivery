package org.example.smart_delivery.service.livreur;

import org.example.smart_delivery.dto.request.LivreurDTO;
import org.example.smart_delivery.entity.Livreur;
import org.example.smart_delivery.mapper.request.LivreurMapper;
import org.example.smart_delivery.repository.LivreurRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class LivreurServiceImpl implements LivreurService {
    private final LivreurRepository livreurRepository;
    private final LivreurMapper livreurMapper;

    @Override
    public LivreurDTO create(LivreurDTO dto) {
        Livreur entity = livreurMapper.toEntity(dto);
        Livreur saved = livreurRepository.save(entity);
        return livreurMapper.toDto(saved);
    }

    @Override
    public LivreurDTO update(String id, LivreurDTO dto) {
        if (!livreurRepository.existsById(id)) {
            throw new IllegalArgumentException("Livreur not found: " + id);
        }
        Livreur entity = livreurMapper.toEntity(dto);
        entity.setId(id);
        Livreur saved = livreurRepository.save(entity);
        return livreurMapper.toDto(saved);
    }

    @Override
    public LivreurDTO getById(String id) {
        Livreur entity = livreurRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Livreur not found: " + id));
        return livreurMapper.toDto(entity);
    }

    @Override
    public List<LivreurDTO> getAll() {
        return livreurRepository.findAll()
                .stream()
                .map(livreurMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public void delete(String id) {
        if (!livreurRepository.existsById(id)) {
            throw new IllegalArgumentException("Livreur not found: " + id);
        }
        livreurRepository.deleteById(id);
    }
}
