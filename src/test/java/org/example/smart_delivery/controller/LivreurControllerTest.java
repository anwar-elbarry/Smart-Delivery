package org.example.smart_delivery.controller;

import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.boot.autoconfigure.security.servlet.SecurityFilterAutoConfiguration;
import org.springframework.boot.autoconfigure.security.servlet.UserDetailsServiceAutoConfiguration;
import org.springframework.boot.autoconfigure.security.oauth2.client.OAuth2ClientAutoConfiguration;
import org.springframework.boot.autoconfigure.security.oauth2.client.servlet.OAuth2ClientWebSecurityAutoConfiguration;
import org.springframework.boot.autoconfigure.security.oauth2.resource.servlet.OAuth2ResourceServerAutoConfiguration;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.test.context.support.WithMockUser;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.example.security.jwt.JwtService;
import org.example.smart_delivery.dto.request.LivreurDTO;
import org.example.smart_delivery.dto.request.UserDTO;
import org.example.smart_delivery.dto.request.ZoneDTO;
import org.example.smart_delivery.dto.response.LivreurRespDTO;
import org.example.smart_delivery.service.livreur.LivreurService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.example.security.service.TokenService;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;
import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WithMockUser
@WebMvcTest(    controllers = LivreurController.class,
        excludeAutoConfiguration = {
                SecurityAutoConfiguration.class,
                UserDetailsServiceAutoConfiguration.class,
                SecurityFilterAutoConfiguration.class,
                OAuth2ClientAutoConfiguration.class,
                OAuth2ResourceServerAutoConfiguration.class,
                OAuth2ClientWebSecurityAutoConfiguration.class
        })
class LivreurControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockitoBean
    private LivreurService livreurService;

    @MockitoBean
    private JwtService jwtService;

    @MockitoBean
    private UserDetailsService userDetailsService;

    @MockitoBean
    private TokenService tokenService;


    private LivreurRespDTO livreurRespDTO;
    private LivreurDTO livreurDTO;
    private UserDTO user;
    private ZoneDTO zoneDTO;
    String userId = UUID.randomUUID().toString();
    String livreurId = UUID.randomUUID().toString();
    String zoneId = UUID.randomUUID().toString();
    @BeforeEach
    void setUp() {


        this.user = UserDTO.builder()
                .id(userId)
                .nom("exp")
                .prenom("expPre")
                .adress("marr")
                .email("exp@example.com")
                .roleId("role123")
                .telephone("087658733")
                .build();

        this.zoneDTO = new ZoneDTO();
        this.zoneDTO.setId(zoneId);
        this.zoneDTO.setNome("marrakech");

        this.livreurDTO = new LivreurDTO();
        livreurDTO.setId(livreurId);
        livreurDTO.setVehicule("Dacia");
        livreurDTO.setUserId(userId);
        livreurDTO.setZoneAssigneeId(zoneId);

        this.livreurRespDTO = new LivreurRespDTO();
        livreurRespDTO.setId(livreurId);
        livreurRespDTO.setVehicule("Dacia");
        livreurRespDTO.setUser(user);
        livreurRespDTO.setZoneAssignee(zoneDTO);
    }
    @Test
    @DisplayName("should create Livreur with success")
    void shouldCreateLivreurWithSuccess() throws Exception{
        when(livreurService.create(any(LivreurDTO.class))).thenReturn(livreurRespDTO);

        mockMvc.perform(post("/api/Livreurs")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(livreurDTO)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(livreurId))
                .andExpect(jsonPath("$.vehicule").value("Dacia"));

        verify(livreurService).create(any(LivreurDTO.class));
    }

    @Test
    @DisplayName("should update Livreur with success")
    void shouldUpdateLivreurWithSuccess() throws Exception {
        String id = livreurId;
        LivreurRespDTO updated = new LivreurRespDTO();
        updated.setId(id);
        updated.setVehicule("Renault");
        updated.setUser(user);
        updated.setZoneAssignee(zoneDTO);

        when(livreurService.update(eq(id), any(LivreurDTO.class))).thenReturn(updated);

        mockMvc.perform(put("/api/Livreurs/{id}", id)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(livreurDTO)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(id))
                .andExpect(jsonPath("$.vehicule").value("Renault"));

        verify(livreurService).update(eq(id), any(LivreurDTO.class));
    }

    @Test
    @DisplayName("should get Livreur by id")
    void shouldGetLivreurById() throws Exception {
        String id = livreurId;
        when(livreurService.getById(id)).thenReturn(livreurRespDTO);

        mockMvc.perform(get("/api/Livreurs/{id}", id))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(id))
                .andExpect(jsonPath("$.vehicule").value("Dacia"));

        verify(livreurService).getById(id);
    }

    @Test
    @DisplayName("should list all Livreurs")
    void shouldListLivreurs() throws Exception {
        List<LivreurRespDTO> list = List.of(livreurRespDTO);
        when(livreurService.getAll()).thenReturn(list);

        mockMvc.perform(get("/api/Livreurs"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(livreurId));

        verify(livreurService).getAll();
    }

    @Test
    @DisplayName("should delete Livreur")
    void shouldDeleteLivreur() throws Exception {
        String id = livreurId;
        doNothing().when(livreurService).delete(id);

        mockMvc.perform(delete("/api/Livreurs/{id}", id))
                .andExpect(status().isNoContent());

        verify(livreurService).delete(id);
    }

}