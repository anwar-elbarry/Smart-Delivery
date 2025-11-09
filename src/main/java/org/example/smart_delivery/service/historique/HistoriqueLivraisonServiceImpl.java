package org.example.smart_delivery.service.historique;

import org.example.smart_delivery.dto.request.HistoriqueLivraisonDTO;
import org.example.smart_delivery.dto.response.HistoriqueLivraisonRespDTO;
import org.example.smart_delivery.entity.HistoriqueLivraison;
import org.example.smart_delivery.mapper.request.HistoLivrMapper;
import org.example.smart_delivery.mapper.response.HistoLivrRespMapper;
import org.example.smart_delivery.repository.HistoriqueLivraisonRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class HistoriqueLivraisonServiceImpl implements HistoriqueLivraisonService {
    private final HistoriqueLivraisonRepository historiqueLivraisonRepository;
    private final HistoLivrMapper histoLivrMapper;
    private final HistoLivrRespMapper histoLivrRespMapper;

    @Override
    public HistoriqueLivraisonRespDTO create(HistoriqueLivraisonDTO dto) {
        HistoriqueLivraison entity = histoLivrMapper.toEntity(dto);
        HistoriqueLivraison saved = historiqueLivraisonRepository.save(entity);
        return histoLivrRespMapper.toRespDto(saved);
    }

    @Override
    public HistoriqueLivraisonRespDTO update(String id, HistoriqueLivraisonDTO dto) {
        if (!historiqueLivraisonRepository.existsById(id)) {
            throw new IllegalArgumentException("HistoriqueLivraison not found: " + id);
        }
        HistoriqueLivraison entity = histoLivrMapper.toEntity(dto);
        entity.setId(id);
        HistoriqueLivraison saved = historiqueLivraisonRepository.save(entity);
        return histoLivrRespMapper.toRespDto(saved);
    }

    @Override
    public HistoriqueLivraisonRespDTO getById(String id) {
        HistoriqueLivraison entity = historiqueLivraisonRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("HistoriqueLivraison not found: " + id));
        return histoLivrRespMapper.toRespDto(entity);
    }

    @Override
    public List<HistoriqueLivraisonRespDTO> getAll() {
        return historiqueLivraisonRepository.findAll()
                .stream()
                .map(histoLivrRespMapper::toRespDto)
                .collect(Collectors.toList());
    }

    @Override
    public void delete(String id) {
        if (!historiqueLivraisonRepository.existsById(id)) {
            throw new IllegalArgumentException("HistoriqueLivraison not found: " + id);
        }
        historiqueLivraisonRepository.deleteById(id);
    }
}
