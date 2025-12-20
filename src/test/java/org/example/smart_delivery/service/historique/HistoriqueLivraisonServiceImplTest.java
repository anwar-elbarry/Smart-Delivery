package org.example.smart_delivery.service.historique;

import org.example.smart_delivery.dto.request.HistoriqueLivraisonDTO;
import org.example.smart_delivery.dto.response.HistoriqueLivraisonRespDTO;
import org.example.smart_delivery.entity.HistoriqueLivraison;
import org.example.smart_delivery.entity.enums.ColisStatus;
import org.example.smart_delivery.mapper.request.HistoLivrMapper;
import org.example.smart_delivery.mapper.response.HistoLivrRespMapper;
import org.example.smart_delivery.repository.HistoriqueLivraisonRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class HistoriqueLivraisonServiceImplTest {

    @Mock
    private HistoriqueLivraisonRepository repository;

    @Mock
    private HistoLivrMapper mapper;

    @Mock
    private HistoLivrRespMapper respMapper;

    @InjectMocks
    private HistoriqueLivraisonServiceImpl service;

    private HistoriqueLivraisonDTO dto;
    private HistoriqueLivraison entity;
    private HistoriqueLivraisonRespDTO resp;

    @BeforeEach
    void setUp() {
        dto = new HistoriqueLivraisonDTO();
        dto.setColisId("c-1");
        dto.setStatut(ColisStatus.IN_TRANSIT);
        dto.setDateChangement(LocalDateTime.of(2025, 1, 1, 12, 0));
        dto.setCommentaire("Initial status");

        entity = HistoriqueLivraison.builder()
                .id("h-1")
                .statut(dto.getStatut())
                .dateChangement(dto.getDateChangement())
                .commentaire(dto.getCommentaire())
                .build();

        resp = new HistoriqueLivraisonRespDTO();
        resp.setId(entity.getId());
        resp.setStatut(entity.getStatut());
        resp.setDateChangement(entity.getDateChangement());
        resp.setCommentaire(entity.getCommentaire());
    }

    @Test
    @DisplayName("create should map, save and return response dto")
    void create_shouldSave() {
        when(mapper.toEntity(dto)).thenReturn(entity);
        when(repository.save(entity)).thenReturn(entity);
        when(respMapper.toRespDto(entity)).thenReturn(resp);

        HistoriqueLivraisonRespDTO result = service.create(dto);

        assertNotNull(result);
        assertEquals(resp.getId(), result.getId());
        verify(mapper).toEntity(dto);
        verify(repository).save(entity);
        verify(respMapper).toRespDto(entity);
    }

    @Test
    @DisplayName("update should save and return dto when exists")
    void update_success() {
        String id = "h-1";
        when(repository.existsById(id)).thenReturn(true);
        when(mapper.toEntity(dto)).thenReturn(entity);
        entity.setId(id);
        when(repository.save(entity)).thenReturn(entity);
        when(respMapper.toRespDto(entity)).thenReturn(resp);

        HistoriqueLivraisonRespDTO result = service.update(id, dto);

        assertNotNull(result);
        assertEquals(resp.getId(), result.getId());
        verify(repository).existsById(id);
        verify(repository).save(entity);
        verify(respMapper).toRespDto(entity);
    }

    @Test
    @DisplayName("update should throw when not exists")
    void update_notFound() {
        String id = "missing";
        when(repository.existsById(id)).thenReturn(false);

        assertThrows(IllegalArgumentException.class, () -> service.update(id, dto));
        verify(repository).existsById(id);
        verify(repository, never()).save(any());
    }

    @Test
    @DisplayName("getById should return dto when found")
    void getById_success() {
        String id = "h-1";
        when(repository.findById(id)).thenReturn(Optional.of(entity));
        when(respMapper.toRespDto(entity)).thenReturn(resp);

        HistoriqueLivraisonRespDTO result = service.getById(id);

        assertNotNull(result);
        assertEquals(resp.getId(), result.getId());
        verify(repository).findById(id);
        verify(respMapper).toRespDto(entity);
    }

    @Test
    @DisplayName("getById should throw when not found")
    void getById_notFound() {
        String id = "missing";
        when(repository.findById(id)).thenReturn(Optional.empty());

        assertThrows(IllegalArgumentException.class, () -> service.getById(id));
        verify(repository).findById(id);
    }

    @Test
    @DisplayName("getAll should return mapped list")
    void getAll_shouldReturnList() {
        HistoriqueLivraison other = HistoriqueLivraison.builder().id("h-2").statut(ColisStatus.DELIVERED).dateChangement(LocalDateTime.now()).commentaire("ok").build();
        HistoriqueLivraisonRespDTO otherResp = new HistoriqueLivraisonRespDTO();
        otherResp.setId(other.getId());
        otherResp.setStatut(other.getStatut());
        otherResp.setDateChangement(other.getDateChangement());
        otherResp.setCommentaire(other.getCommentaire());

        when(repository.findAll()).thenReturn(List.of(entity, other));
        when(respMapper.toRespDto(entity)).thenReturn(resp);
        when(respMapper.toRespDto(other)).thenReturn(otherResp);

        var result = service.getAll();

        assertNotNull(result);
        assertEquals(2, result.size());
        assertTrue(result.stream().anyMatch(r -> r.getId().equals(resp.getId())));
        verify(repository).findAll();
    }

    @Test
    @DisplayName("delete should remove when exists")
    void delete_success() {
        String id = "h-1";
        when(repository.existsById(id)).thenReturn(true);

        service.delete(id);

        verify(repository).existsById(id);
        verify(repository).deleteById(id);
    }

    @Test
    @DisplayName("delete should throw when not exists")
    void delete_notFound() {
        String id = "missing";
        when(repository.existsById(id)).thenReturn(false);

        assertThrows(IllegalArgumentException.class, () -> service.delete(id));
        verify(repository).existsById(id);
        verify(repository, never()).deleteById(any());
    }
}
