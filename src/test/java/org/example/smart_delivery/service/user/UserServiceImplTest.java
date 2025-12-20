package org.example.smart_delivery.service.user;

import org.example.smart_delivery.dto.request.UserDTO;
import org.example.smart_delivery.dto.response.UserRespDTO;
import org.example.smart_delivery.entity.User;
import org.example.smart_delivery.exception.ResourceNotFoundException;
import org.example.smart_delivery.mapper.request.UserMapper;
import org.example.smart_delivery.mapper.response.UserRespMapper;
import org.example.smart_delivery.repository.UserRepository;
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

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserServiceImplTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private UserMapper userMapper;

    @Mock
    private UserRespMapper userRespMapper;

    @InjectMocks
    private UserServiceImpl userService;

    private UserDTO sampleDto;
    private User sampleEntity;
    private UserRespDTO sampleResp;

    @BeforeEach
    void setUp() {
        sampleDto = new UserDTO();
        sampleDto.setNom("Dupont");
        sampleDto.setPrenom("Jean");
        sampleDto.setEmail("jean.dupont@example.com");
        sampleDto.setTelephone("+33612345678");
        sampleDto.setAdress("1 rue de la Paix");

        sampleEntity = User.builder()
                .id("user-1")
                .nom(sampleDto.getNom())
                .prenom(sampleDto.getPrenom())
                .email(sampleDto.getEmail())
                .telephone(sampleDto.getTelephone())
                .adress(sampleDto.getAdress())
                .build();

        sampleResp = new UserRespDTO();
        sampleResp.setId(sampleEntity.getId());
        sampleResp.setNom(sampleEntity.getNom());
        sampleResp.setPrenom(sampleEntity.getPrenom());
        sampleResp.setEmail(sampleEntity.getEmail());
        sampleResp.setTelephone(sampleEntity.getTelephone());
        sampleResp.setAdress(sampleEntity.getAdress());
    }

    @Test
    @DisplayName("create should save entity and return response dto")
    void create() {
        when(userMapper.toEntity(sampleDto)).thenReturn(sampleEntity);
        when(userRepository.save(sampleEntity)).thenReturn(sampleEntity);
        when(userRespMapper.toRespDto(sampleEntity)).thenReturn(sampleResp);

        UserRespDTO result = userService.create(sampleDto);

        assertNotNull(result);
        assertEquals(sampleResp.getId(), result.getId());
        verify(userMapper).toEntity(sampleDto);
        verify(userRepository).save(sampleEntity);
        verify(userRespMapper).toRespDto(sampleEntity);
    }

    @Test
    @DisplayName("update should modify and return updated dto when user exists")
    void update_success() {
        String id = "user-1";
        when(userRepository.findById(id)).thenReturn(Optional.of(sampleEntity));
        // userMapper.updateEntityFromDto will be called; we don't change entity here
        when(userRepository.save(sampleEntity)).thenReturn(sampleEntity);
        when(userRespMapper.toRespDto(sampleEntity)).thenReturn(sampleResp);

        UserRespDTO result = userService.update(id, sampleDto);

        assertNotNull(result);
        assertEquals(sampleResp.getId(), result.getId());
        verify(userRepository).findById(id);
        verify(userRepository).save(sampleEntity);
        verify(userRespMapper).toRespDto(sampleEntity);
    }

    @Test
    @DisplayName("update should throw IllegalArgumentException when user not found")
    void update_notFound() {
        String id = "missing";
        when(userRepository.findById(id)).thenReturn(Optional.empty());

        assertThrows(IllegalArgumentException.class, () -> userService.update(id, sampleDto));
        verify(userRepository).findById(id);
        verify(userRepository, never()).save(any());
    }

    @Test
    @DisplayName("getById should return response dto when found")
    void getById_success() {
        String id = "user-1";
        when(userRepository.findById(id)).thenReturn(Optional.of(sampleEntity));
        when(userRespMapper.toRespDto(sampleEntity)).thenReturn(sampleResp);

        UserRespDTO result = userService.getById(id);

        assertNotNull(result);
        assertEquals(sampleResp.getId(), result.getId());
        verify(userRepository).findById(id);
        verify(userRespMapper).toRespDto(sampleEntity);
    }

    @Test
    @DisplayName("getById should throw ResourceNotFoundException when not found")
    void getById_notFound() {
        String id = "missing";
        when(userRepository.findById(id)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> userService.getById(id));
        verify(userRepository).findById(id);
    }

    @Test
    @DisplayName("getAll should return mapped list")
    void getAll() {
        User other = User.builder().id("u2").nom("A").prenom("B").email("a@b.com").telephone("+331").adress("adr").build();
        UserRespDTO otherResp = new UserRespDTO();
        otherResp.setId(other.getId());
        when(userRepository.findAll()).thenReturn(List.of(sampleEntity, other));
        when(userRespMapper.toRespDto(sampleEntity)).thenReturn(sampleResp);
        when(userRespMapper.toRespDto(other)).thenReturn(otherResp);

        List<UserRespDTO> result = userService.getAll();

        assertNotNull(result);
        assertEquals(2, result.size());
        assertTrue(result.stream().anyMatch(r -> r.getId().equals(sampleResp.getId())));
        verify(userRepository).findAll();
    }

    @Test
    @DisplayName("delete should remove when exists")
    void delete_success() {
        String id = "user-1";
        when(userRepository.existsById(id)).thenReturn(true);

        userService.delete(id);

        verify(userRepository).existsById(id);
        verify(userRepository).deleteById(id);
    }

    @Test
    @DisplayName("delete should throw IllegalArgumentException when not exists")
    void delete_notFound() {
        String id = "missing";
        when(userRepository.existsById(id)).thenReturn(false);

        assertThrows(IllegalArgumentException.class, () -> userService.delete(id));
        verify(userRepository).existsById(id);
        verify(userRepository, never()).deleteById(any());
    }

    @Test
    @DisplayName("search should return mapped page")
    void search() {
        String q = "dup";
        Pageable pageable = PageRequest.of(0, 10);
        Page<User> page = new PageImpl<>(List.of(sampleEntity), pageable, 1);
        when(userRepository.search(q, pageable)).thenReturn(page);
        when(userRespMapper.toRespDto(sampleEntity)).thenReturn(sampleResp);

        Page<UserRespDTO> result = userService.search(q, pageable);

        assertNotNull(result);
        assertEquals(1, result.getTotalElements());
        assertEquals(sampleResp.getId(), result.getContent().get(0).getId());
        verify(userRepository).search(q, pageable);
        verify(userRespMapper).toRespDto(sampleEntity);
    }
}