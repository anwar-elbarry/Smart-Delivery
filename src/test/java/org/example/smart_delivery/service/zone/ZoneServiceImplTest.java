package org.example.smart_delivery.service.zone;

import org.example.smart_delivery.dto.request.ZoneDTO;
import org.example.smart_delivery.dto.response.ZoneRespDTO;
import org.example.smart_delivery.entity.Zone;
import org.example.smart_delivery.mapper.request.ZoneMapper;
import org.example.smart_delivery.mapper.response.ZoneRespMapper;
import org.example.smart_delivery.repository.ZoneRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ZoneServiceImplTest {

    @Mock
    private ZoneRepository zoneRepository;

    @Mock
    private ZoneMapper zoneMapper;

    @Mock
    private ZoneRespMapper zoneRespMapper;

    @InjectMocks
    private ZoneServiceImpl zoneService;

    private ZoneDTO dto;
    private Zone entity;
    private ZoneRespDTO resp;

    @BeforeEach
    void setUp() {
        dto = new ZoneDTO();
        dto.setNome("Paris Centre");
        dto.setCodePostal(75001);

        entity = Zone.builder()
                .id("z-1")
                .nome(dto.getNome())
                .codePostal(dto.getCodePostal())
                .build();

        resp = new ZoneRespDTO();
        resp.setId(entity.getId());
        resp.setNome(entity.getNome());
        resp.setCodePostal(entity.getCodePostal());
    }

    @Test
    @DisplayName("create should save entity and return response dto")
    void create_shouldSave() {
        when(zoneMapper.toEntity(dto)).thenReturn(entity);
        when(zoneRepository.save(entity)).thenReturn(entity);
        when(zoneRespMapper.toRespDto(entity)).thenReturn(resp);

        ZoneRespDTO result = zoneService.create(dto);

        assertNotNull(result);
        assertEquals(resp.getId(), result.getId());
        verify(zoneMapper).toEntity(dto);
        verify(zoneRepository).save(entity);
        verify(zoneRespMapper).toRespDto(entity);
    }

    @Test
    @DisplayName("update should modify and return dto when zone exists")
    void update_success() {
        String id = "z-1";
        when(zoneRepository.findById(id)).thenReturn(Optional.of(entity));
        // zoneMapper.updateEntityFromDto will be called; keep entity unchanged here
        when(zoneRepository.save(entity)).thenReturn(entity);
        when(zoneRespMapper.toRespDto(entity)).thenReturn(resp);

        ZoneRespDTO result = zoneService.update(id, dto);

        assertNotNull(result);
        assertEquals(resp.getId(), result.getId());
        verify(zoneRepository).findById(id);
        verify(zoneMapper).updateEntityFromDto(dto, entity);
        verify(zoneRepository).save(entity);
        verify(zoneRespMapper).toRespDto(entity);
    }

    @Test
    @DisplayName("update should throw when zone not found")
    void update_notFound() {
        String id = "missing";
        when(zoneRepository.findById(id)).thenReturn(Optional.empty());

        assertThrows(IllegalArgumentException.class, () -> zoneService.update(id, dto));
        verify(zoneRepository).findById(id);
        verify(zoneRepository, never()).save(any());
    }

    @Test
    @DisplayName("getById should return dto when found")
    void getById_success() {
        String id = "z-1";
        when(zoneRepository.findById(id)).thenReturn(Optional.of(entity));
        when(zoneRespMapper.toRespDto(entity)).thenReturn(resp);

        ZoneRespDTO result = zoneService.getById(id);

        assertNotNull(result);
        assertEquals(resp.getId(), result.getId());
        verify(zoneRepository).findById(id);
        verify(zoneRespMapper).toRespDto(entity);
    }

    @Test
    @DisplayName("getById should throw when not found")
    void getById_notFound() {
        String id = "missing";
        when(zoneRepository.findById(id)).thenReturn(Optional.empty());

        assertThrows(IllegalArgumentException.class, () -> zoneService.getById(id));
        verify(zoneRepository).findById(id);
    }

    @Test
    @DisplayName("getAll should return mapped list")
    void getAll_shouldReturnList() {
        Zone other = Zone.builder().id("z-2").nome("Banlieue").codePostal(92000).build();
        ZoneRespDTO otherResp = new ZoneRespDTO();
        otherResp.setId(other.getId());
        otherResp.setNome(other.getNome());
        otherResp.setCodePostal(other.getCodePostal());

        when(zoneRepository.findAll()).thenReturn(List.of(entity, other));
        when(zoneRespMapper.toRespDto(entity)).thenReturn(resp);
        when(zoneRespMapper.toRespDto(other)).thenReturn(otherResp);

        List<ZoneRespDTO> result = zoneService.getAll();

        assertNotNull(result);
        assertEquals(2, result.size());
        assertTrue(result.stream().anyMatch(r -> r.getId().equals(resp.getId())));
        verify(zoneRepository).findAll();
    }

    @Test
    @DisplayName("delete should remove when exists")
    void delete_success() {
        String id = "z-1";
        when(zoneRepository.findById(id)).thenReturn(Optional.of(entity));

        zoneService.delete(id);

        verify(zoneRepository).findById(id);
        verify(zoneRepository).delete(entity);
    }

    @Test
    @DisplayName("delete should throw when not exists")
    void delete_notFound() {
        String id = "missing";
        when(zoneRepository.findById(id)).thenReturn(Optional.empty());

        assertThrows(IllegalArgumentException.class, () -> zoneService.delete(id));
        verify(zoneRepository).findById(id);
        verify(zoneRepository, never()).delete(any());
    }
}

