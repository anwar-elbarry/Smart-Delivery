package org.example.smart_delivery.service.colis;

import org.example.smart_delivery.dto.ColisDTO;
import org.example.smart_delivery.dto.LivreurDTO;
import org.example.smart_delivery.entity.Colis;
import org.example.smart_delivery.entity.Livreur;
import org.example.smart_delivery.entity.enums.ColisStatus;
import org.example.smart_delivery.exception.BusinessException;
import org.example.smart_delivery.exception.ResourceNotFoundException;
import org.example.smart_delivery.mapper.ColisMapper;
import org.example.smart_delivery.mapper.LivreurMapper;
import org.example.smart_delivery.repository.ColisRepository;
import lombok.RequiredArgsConstructor;
import org.example.smart_delivery.repository.LivreurRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ColisServiceImpl implements ColisService {
    private final ColisRepository colisRepository;
    private final ColisMapper colisMapper;
    private final LivreurMapper livreurMapper;
    private final LivreurRepository livreurRepository;

    @Override
    public ColisDTO create(ColisDTO dto) {
        Colis entity = colisMapper.toEntity(dto);
        Colis saved = colisRepository.save(entity);
        return colisMapper.toDto(saved);
    }

    @Override
    public ColisDTO update(String id, ColisDTO dto) {
        if (!colisRepository.existsById(id)) {
            throw new ResourceNotFoundException("Colis",id);
        }
        Colis entity = colisMapper.toEntity(dto);
        entity.setId(id);
        Colis saved = colisRepository.save(entity);
        return colisMapper.toDto(saved);
    }

    @Override
    public ColisDTO getById(String id) {
        Colis entity = colisRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Colis",id));
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
            throw new ResourceNotFoundException("Colis",id);
        }
        colisRepository.deleteById(id);
    }

    @Override
    public void Assign_col(String colisId,String livreurId) {
        Colis colis = colisRepository.findById(colisId)
                .orElseThrow(() -> new ResourceNotFoundException("Colis",colisId));
        Livreur livreur = livreurRepository.findById(livreurId)
                .orElseThrow(() -> new ResourceNotFoundException("Livreur",livreurId));
        colis.setLivreur(livreur);
        colisRepository.save(colis);
    }

    @Override
    public List<ColisDTO> filterByStatus(ColisStatus status) {
        return  colisRepository.findColisByStatut(status).stream().map(colisMapper::toDto).toList();
    }
}
