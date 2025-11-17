package org.example.smart_delivery.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.example.smart_delivery.dto.request.UserDTO;
import org.example.smart_delivery.dto.response.UserRespDTO;
import org.example.smart_delivery.entity.enums.UserRole;
import org.example.smart_delivery.service.user.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.Arrays;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(UserController.class)
class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockitoBean
    private UserService userService;

    private UserDTO userDTO;
    private UserRespDTO userRespDTO;

    @BeforeEach
    void setUp() {
        userDTO = UserDTO.builder()
                .id("user-1")
                .nom("John")
                .prenom("Doe")
                .email("john@example.com")
                .telephone("0123456")
                .adress("123 main")
                .role(UserRole.LIVREUR)
                .build();

        userRespDTO = UserRespDTO.builder()
                .id("user-1")
                .nom("John")
                .prenom("Doe")
                .email("john@example.com")
                .adress("123 main")
                .telephone("0123456")
                .role(UserRole.LIVREUR)
                .build();
    }

    @Test
    void shouldCreateUser() throws Exception {
        when(userService.create(any(UserDTO.class))).thenReturn(userRespDTO);

        mockMvc.perform(post("/api/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(userDTO)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value("user-1"));

        verify(userService).create(any(UserDTO.class));
    }

    @Test
    void shouldUpdateUser() throws Exception {
        UserRespDTO updated = UserRespDTO.builder().id("user-1").nom("Jane").build();
        when(userService.update(eq("user-1"), any(UserDTO.class))).thenReturn(updated);

        mockMvc.perform(put("/api/users/{id}", "user-1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(userDTO)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.nom").value("Jane"));

        verify(userService).update(eq("user-1"), any(UserDTO.class));
    }

    @Test
    void shouldGetUserById() throws Exception {
        when(userService.getById(eq("user-1"))).thenReturn(userRespDTO);

        mockMvc.perform(get("/api/users/{id}", "user-1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value("user-1"));

        verify(userService).getById(eq("user-1"));
    }

    @Test
    void shouldListUsers() throws Exception {
        List<UserRespDTO> list = Arrays.asList(userRespDTO);
        PageImpl<UserRespDTO> page = new PageImpl<>(list, PageRequest.of(0,10), list.size());
        when(userService.getAll()).thenReturn(list);

        mockMvc.perform(get("/api/users"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value("user-1"));

        verify(userService).getAll();
    }

    @Test
    void shouldDeleteUser() throws Exception {
        mockMvc.perform(delete("/api/users/{id}", "user-1"))
                .andExpect(status().isNoContent());

        verify(userService).delete(eq("user-1"));
    }

    @Test
    void shouldSearchUsers() throws Exception {
        PageImpl<UserRespDTO> page = new PageImpl<>(Arrays.asList(userRespDTO), PageRequest.of(0,10), 1);
        when(userService.search(eq("john"), any(Pageable.class))).thenReturn(page);

        mockMvc.perform(get("/api/users/search")
                        .param("q", "john")
                        .param("page","0")
                        .param("size","10"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content[0].id").value("user-1"));

        verify(userService).search(eq("john"), any(Pageable.class));
    }
}
