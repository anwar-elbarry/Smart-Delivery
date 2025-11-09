package org.example.smart_delivery.service.produit;

import org.example.smart_delivery.dto.request.ProduitDTO;
import org.example.smart_delivery.dto.response.ProduitRespDTO;
import org.example.smart_delivery.entity.Produit;
import org.example.smart_delivery.mapper.request.ProduitMapper;
import org.example.smart_delivery.mapper.response.ProduitRespMapper;
import org.example.smart_delivery.repository.ProduitRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ProduitServiceImpl implements ProduitService {
    private final ProduitRepository produitRepository;
    private final ProduitMapper produitMapper;
    private final ProduitRespMapper produitRespMapper;

    @Override
    public ProduitRespDTO create(ProduitDTO dto) {
        Produit entity = produitMapper.toEntity(dto);
        Produit saved = produitRepository.save(entity);
        return produitRespMapper.toRespDto(saved);
    }

    @Override
    public ProduitRespDTO update(String id, ProduitDTO dto) {
        Produit entity = produitRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Produit not found: " + id));
        produitMapper.updateEntityFromDto(dto, entity);
        Produit saved = produitRepository.save(entity);
        return produitRespMapper.toRespDto(saved);
    }

    @Override
    public ProduitRespDTO getById(String id) {
        Produit entity = produitRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Produit not found: " + id));
        return produitRespMapper.toRespDto(entity);
    }

    @Override
    public Page<ProduitRespDTO> getAll(Pageable pageable) {
        return produitRepository.findAll(pageable)
                .map(produitRespMapper::toRespDto);
    }

    @Override
    public void delete(String id) {
        if (!produitRepository.existsById(id)) {
            throw new IllegalArgumentException("Produit not found: " + id);
        }
        produitRepository.deleteById(id);
    }
}
