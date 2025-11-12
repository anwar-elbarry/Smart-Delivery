package org.example.smart_delivery.service.produit;

import org.example.smart_delivery.dto.request.ProduitDTO;
import org.example.smart_delivery.dto.response.ProduitRespDTO;
import org.example.smart_delivery.entity.Produit;
import org.example.smart_delivery.mapper.request.ProduitMapper;
import org.example.smart_delivery.mapper.response.ProduitRespMapper;
import org.example.smart_delivery.repository.ProduitRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ProduitServiceImplTest {

    @Mock
    private ProduitRepository produitRepository;

    @Mock
    private ProduitMapper produitMapper;

    @Mock
    private ProduitRespMapper produitRespMapper;

    @InjectMocks
    private ProduitServiceImpl produitService;

    private ProduitDTO dto;
    private Produit entity;
    private ProduitRespDTO resp;

    @BeforeEach
    void setUp() {
        dto = new ProduitDTO();
        dto.setId(null);
        dto.setNom("Produit A");
        dto.setCategorie("Cat A");
        dto.setPoids(2.5);
        dto.setPrix(BigDecimal.valueOf(10.0));

        entity = Produit.builder()
                .id("p-1")
                .nom(dto.getNom())
                .categorie(dto.getCategorie())
                .poids(dto.getPoids())
                .prix(dto.getPrix())
                .build();

        resp = new ProduitRespDTO();
        resp.setId(entity.getId());
        resp.setNom(entity.getNom());
        resp.setCategorie(entity.getCategorie());
        resp.setPoids(entity.getPoids());
        resp.setPrix(entity.getPrix());
    }

    @Test
    @DisplayName("create should save and return response dto")
    void create_shouldSave() {
        when(produitMapper.toEntity(dto)).thenReturn(entity);
        when(produitRepository.save(entity)).thenReturn(entity);
        when(produitRespMapper.toRespDto(entity)).thenReturn(resp);

        ProduitRespDTO result = produitService.create(dto);

        assertNotNull(result);
        assertEquals(resp.getId(), result.getId());
        verify(produitMapper).toEntity(dto);
        verify(produitRepository).save(entity);
        verify(produitRespMapper).toRespDto(entity);
    }

    @Test
    @DisplayName("update should return updated dto when produit exists")
    void update_success() {
        String id = "p-1";
        when(produitRepository.findById(id)).thenReturn(Optional.of(entity));
        // mapper.updateEntityFromDto called inside service
        when(produitRepository.save(entity)).thenReturn(entity);
        when(produitRespMapper.toRespDto(entity)).thenReturn(resp);

        ProduitRespDTO result = produitService.update(id, dto);

        assertNotNull(result);
        assertEquals(resp.getId(), result.getId());
        verify(produitRepository).findById(id);
        verify(produitRepository).save(entity);
        verify(produitRespMapper).toRespDto(entity);
    }

    @Test
    @DisplayName("update should throw IllegalArgumentException when produit not found")
    void update_notFound() {
        String id = "missing";
        when(produitRepository.findById(id)).thenReturn(Optional.empty());

        assertThrows(IllegalArgumentException.class, () -> produitService.update(id, dto));
        verify(produitRepository).findById(id);
        verify(produitRepository, never()).save(any());
    }

    @Test
    @DisplayName("getById should return dto when found")
    void getById_success() {
        String id = "p-1";
        when(produitRepository.findById(id)).thenReturn(Optional.of(entity));
        when(produitRespMapper.toRespDto(entity)).thenReturn(resp);

        ProduitRespDTO result = produitService.getById(id);

        assertNotNull(result);
        assertEquals(resp.getId(), result.getId());
        verify(produitRepository).findById(id);
        verify(produitRespMapper).toRespDto(entity);
    }

    @Test
    @DisplayName("getById should throw IllegalArgumentException when not found")
    void getById_notFound() {
        String id = "missing";
        when(produitRepository.findById(id)).thenReturn(Optional.empty());

        assertThrows(IllegalArgumentException.class, () -> produitService.getById(id));
        verify(produitRepository).findById(id);
    }

    @Test
    @DisplayName("getAll should return page mapped to response dtos")
    void getAll() {
        Pageable pageable = PageRequest.of(0, 10);
        Page<Produit> page = new PageImpl<>(List.of(entity), pageable, 1);
        when(produitRepository.findAll(pageable)).thenReturn(page);
        when(produitRespMapper.toRespDto(entity)).thenReturn(resp);

        Page<ProduitRespDTO> result = produitService.getAll(pageable);

        assertNotNull(result);
        assertEquals(1, result.getTotalElements());
        assertEquals(resp.getId(), result.getContent().get(0).getId());
        verify(produitRepository).findAll(pageable);
        verify(produitRespMapper).toRespDto(entity);
    }

    @Test
    @DisplayName("delete should remove when exists")
    void delete_success() {
        String id = "p-1";
        when(produitRepository.existsById(id)).thenReturn(true);

        produitService.delete(id);

        verify(produitRepository).existsById(id);
        verify(produitRepository).deleteById(id);
    }

    @Test
    @DisplayName("delete should throw IllegalArgumentException when not exists")
    void delete_notFound() {
        String id = "missing";
        when(produitRepository.existsById(id)).thenReturn(false);

        assertThrows(IllegalArgumentException.class, () -> produitService.delete(id));
        verify(produitRepository).existsById(id);
        verify(produitRepository, never()).deleteById(any());
    }
}