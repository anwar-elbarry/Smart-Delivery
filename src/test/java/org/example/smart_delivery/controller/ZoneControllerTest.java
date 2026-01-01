package org.example.smart_delivery.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.example.smart_delivery.dto.request.ZoneDTO;
import org.example.smart_delivery.dto.response.ZoneRespDTO;
import org.example.smart_delivery.service.zone.ZoneService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.boot.autoconfigure.security.servlet.SecurityFilterAutoConfiguration;
import org.springframework.boot.autoconfigure.security.servlet.UserDetailsServiceAutoConfiguration;
import org.springframework.boot.autoconfigure.security.oauth2.client.OAuth2ClientAutoConfiguration;
import org.springframework.boot.autoconfigure.security.oauth2.client.servlet.OAuth2ClientWebSecurityAutoConfiguration;
import org.springframework.boot.autoconfigure.security.oauth2.resource.servlet.OAuth2ResourceServerAutoConfiguration;
import org.springframework.security.test.context.support.WithMockUser;
import org.example.security.jwt.JwtService;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.example.security.service.TokenService;

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

@WithMockUser
@WebMvcTest(controllers = ZoneController.class,
        excludeAutoConfiguration = {
                SecurityAutoConfiguration.class,
                UserDetailsServiceAutoConfiguration.class,
                SecurityFilterAutoConfiguration.class,
                OAuth2ClientAutoConfiguration.class,
                OAuth2ResourceServerAutoConfiguration.class,
                OAuth2ClientWebSecurityAutoConfiguration.class
        })
class ZoneControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockitoBean
    private ZoneService zoneService;

    @MockitoBean
    private JwtService jwtService;

    @MockitoBean
    private UserDetailsService userDetailsService;

    @MockitoBean
    private TokenService tokenService;

    private ZoneDTO zoneDTO;
    private ZoneRespDTO zoneRespDTO;

    @BeforeEach
    void setUp() {
        zoneDTO = new ZoneDTO();
        zoneDTO.setId("zone-1");
        zoneDTO.setNome("Zone A");
        zoneRespDTO = new ZoneRespDTO();
        zoneRespDTO.setId("zone-1");
        zoneRespDTO.setNome("Zone A");
    }

    @Test
    void shouldCreateZone() throws Exception {
        when(zoneService.create(any(ZoneDTO.class))).thenReturn(zoneRespDTO);

        mockMvc.perform(post("/api/zones")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(zoneDTO)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value("zone-1"))
                .andExpect(jsonPath("$.nome").value("Zone A"));

        verify(zoneService).create(any(ZoneDTO.class));
    }

    @Test
    void shouldGetAllZones() throws Exception {
        List<ZoneRespDTO> list = Arrays.asList(zoneRespDTO);
        when(zoneService.getAll()).thenReturn(list);

        mockMvc.perform(get("/api/zones"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value("zone-1"));

        verify(zoneService).getAll();
    }

    @Test
    void shouldGetZoneById() throws Exception {
        when(zoneService.getById(eq("zone-1"))).thenReturn(zoneRespDTO);

        mockMvc.perform(get("/api/zones/{id}", "zone-1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value("zone-1"));

        verify(zoneService).getById(eq("zone-1"));
    }

    @Test
    void shouldUpdateZone() throws Exception {
        when(zoneService.update(eq("zone-1"), any(ZoneDTO.class))).thenReturn(zoneRespDTO);

        mockMvc.perform(put("/api/zones/{id}", "zone-1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(zoneDTO)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.nome").value("Zone A"));

        verify(zoneService).update(eq("zone-1"), any(ZoneDTO.class));
    }

    @Test
    void shouldDeleteZone() throws Exception {
        mockMvc.perform(delete("/api/zones/{id}", "zone-1"))
                .andExpect(status().isNoContent());

        verify(zoneService).delete(eq("zone-1"));
    }
}