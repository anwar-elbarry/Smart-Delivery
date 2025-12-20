package org.example.smart_delivery.service.livreur;

import org.example.smart_delivery.dto.request.LivreurDTO;
import org.example.smart_delivery.dto.request.UserDTO;
import org.example.smart_delivery.dto.request.ZoneDTO;
import org.example.smart_delivery.dto.response.LivreurRespDTO;
import org.example.smart_delivery.entity.Livreur;
import org.example.smart_delivery.mapper.request.LivreurMapper;
import org.example.smart_delivery.mapper.response.LivreurRespMapper;
import org.example.smart_delivery.repository.LivreurRepository;
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
class LivreurServiceImplTest {

    @Mock
    private LivreurRepository livreurRepository;

    @Mock
    private LivreurMapper livreurMapper;

    @Mock
    private LivreurRespMapper livreurRespMapper;

    @InjectMocks
    private LivreurServiceImpl livreurService;

    private LivreurDTO dto;
    private Livreur entity;
    private LivreurRespDTO resp;

    @BeforeEach
    void setUp() {
        dto = new LivreurDTO();
        dto.setUserId("user-1");
        dto.setVehicule("Vespa");
        dto.setZoneAssigneeId("zone-1");

        entity = Livreur.builder()
                .id("l-1")
                .vehicule(dto.getVehicule())
                .build();

        resp = new LivreurRespDTO();
        resp.setId(entity.getId());
        resp.setVehicule(entity.getVehicule());
        resp.setUser(new UserDTO());
        resp.setZoneAssignee(new ZoneDTO());
    }

    @Test
    @DisplayName("create should map, save and return response dto")
    void create_shouldSaveAndReturn() {
        when(livreurMapper.toEntity(dto)).thenReturn(entity);
        when(livreurRepository.save(entity)).thenReturn(entity);
        when(livreurRespMapper.toRespDto(entity)).thenReturn(resp);

        LivreurRespDTO result = livreurService.create(dto);

        assertNotNull(result);
        assertEquals(resp.getId(), result.getId());
        verify(livreurMapper).toEntity(dto);
        verify(livreurRepository).save(entity);
        verify(livreurRespMapper).toRespDto(entity);
    }

    @Test
    @DisplayName("update should save when exists and return dto")
    void update_success() {
        String id = "l-1";
        when(livreurRepository.existsById(id)).thenReturn(true);
        when(livreurMapper.toEntity(dto)).thenReturn(entity);
        when(livreurRepository.save(any(Livreur.class))).thenReturn(entity);
        when(livreurRespMapper.toRespDto(entity)).thenReturn(resp);

        LivreurRespDTO result = livreurService.update(id, dto);

        assertNotNull(result);
        assertEquals(resp.getId(), result.getId());
        verify(livreurRepository).existsById(id);
        verify(livreurRepository).save(entity);
        verify(livreurRespMapper).toRespDto(entity);
    }

    @Test
    @DisplayName("update should throw when entity does not exist")
    void update_notFound() {
        String id = "missing";
        when(livreurRepository.existsById(id)).thenReturn(false);

        assertThrows(IllegalArgumentException.class, () -> livreurService.update(id, dto));
        verify(livreurRepository).existsById(id);
        verify(livreurRepository, never()).save(any());
    }

    @Test
    @DisplayName("getById should return mapped dto when found")
    void getById_success() {
        String id = "l-1";
        when(livreurRepository.findById(id)).thenReturn(Optional.of(entity));
        when(livreurRespMapper.toRespDto(entity)).thenReturn(resp);

        LivreurRespDTO result = livreurService.getById(id);

        assertNotNull(result);
        assertEquals(resp.getId(), result.getId());
        verify(livreurRepository).findById(id);
        verify(livreurRespMapper).toRespDto(entity);
    }

    @Test
    @DisplayName("getById should throw when not found")
    void getById_notFound() {
        String id = "missing";
        when(livreurRepository.findById(id)).thenReturn(Optional.empty());

        assertThrows(IllegalArgumentException.class, () -> livreurService.getById(id));
        verify(livreurRepository).findById(id);
    }

    @Test
    @DisplayName("getAll should return mapped list")
    void getAll_shouldReturnList() {
        Livreur other = Livreur.builder().id("l-2").vehicule("Bike").build();
        LivreurRespDTO otherResp = new LivreurRespDTO();
        otherResp.setId(other.getId());
        otherResp.setVehicule(other.getVehicule());

        when(livreurRepository.findAll()).thenReturn(List.of(entity, other));
        when(livreurRespMapper.toRespDto(entity)).thenReturn(resp);
        when(livreurRespMapper.toRespDto(other)).thenReturn(otherResp);

        List<LivreurRespDTO> result = livreurService.getAll();

        assertNotNull(result);
        assertEquals(2, result.size());
        assertTrue(result.stream().anyMatch(r -> r.getId().equals(resp.getId())));
        verify(livreurRepository).findAll();
    }

    @Test
    @DisplayName("delete should remove when exists")
    void delete_success() {
        String id = "l-1";
        when(livreurRepository.existsById(id)).thenReturn(true);

        livreurService.delete(id);

        verify(livreurRepository).existsById(id);
        verify(livreurRepository).deleteById(id);
    }

    @Test
    @DisplayName("delete should throw when not exists")
    void delete_notFound() {
        String id = "missing";
        when(livreurRepository.existsById(id)).thenReturn(false);

        assertThrows(IllegalArgumentException.class, () -> livreurService.delete(id));
        verify(livreurRepository).existsById(id);
        verify(livreurRepository, never()).deleteById(any());
    }
}