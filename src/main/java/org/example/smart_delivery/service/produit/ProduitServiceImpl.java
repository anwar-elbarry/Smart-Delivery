package org.example.smart_delivery.service.produit;

import org.example.smart_delivery.dto.request.ProduitDTO;
import org.example.smart_delivery.entity.Produit;
import org.example.smart_delivery.mapper.request.ProduitMapper;
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

    @Override
    public ProduitDTO create(ProduitDTO dto) {
        Produit entity = produitMapper.toEntity(dto);
        Produit saved = produitRepository.save(entity);
        return produitMapper.toDto(saved);
    }

    @Override
    public ProduitDTO update(String id, ProduitDTO dto) {
        Produit entity = produitRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Produit not found: " + id));
        produitMapper.updateEntityFromDto(dto, entity);
        Produit saved = produitRepository.save(entity);
        return produitMapper.toDto(saved);
    }

    @Override
    public ProduitDTO getById(String id) {
        Produit entity = produitRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Produit not found: " + id));
        return produitMapper.toDto(entity);
    }

    @Override
    public Page<ProduitDTO> getAll(Pageable pageable) {
        return produitRepository.findAll(pageable)
                .map(produitMapper::toDto);
    }

    @Override
    public void delete(String id) {
        if (!produitRepository.existsById(id)) {
            throw new IllegalArgumentException("Produit not found: " + id);
        }
        produitRepository.deleteById(id);
    }
}
