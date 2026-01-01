package org.example.smart_delivery.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.example.smart_delivery.dto.request.ProduitDTO;
import org.example.smart_delivery.dto.response.ProduitRespDTO;
import org.example.smart_delivery.service.produit.ProduitService;
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

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.anyString;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WithMockUser
@WebMvcTest(controllers = ProduitController.class,
        excludeAutoConfiguration = {
                SecurityAutoConfiguration.class,
                UserDetailsServiceAutoConfiguration.class,
                SecurityFilterAutoConfiguration.class,
                OAuth2ClientAutoConfiguration.class,
                OAuth2ResourceServerAutoConfiguration.class,
                OAuth2ClientWebSecurityAutoConfiguration.class
        })
class ProduitControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockitoBean
    private ProduitService produitService;

    @MockitoBean
    private JwtService jwtService;

    @MockitoBean
    private UserDetailsService userDetailsService;

    @MockitoBean
    private TokenService tokenService;

    private ProduitDTO produitDTO;
    private ProduitRespDTO produitRespDTO;

    @BeforeEach
    void setUp() {


        produitDTO = new ProduitDTO();
        produitDTO.setId("prod-1");
        produitDTO.setNom("Produit A");
        produitDTO.setCategorie("cat");
        produitDTO.setPrix(BigDecimal.valueOf(12.5));

        produitRespDTO = new ProduitRespDTO();
        produitRespDTO.setId("prod-1");
        produitRespDTO.setNom("Produit A");
        produitRespDTO.setCategorie("cat");
        produitRespDTO.setPrix(BigDecimal.valueOf(12.5));
    }

    @Test
    void shouldCreateProduit() throws Exception {
        when(produitService.create(any(ProduitDTO.class))).thenReturn(produitRespDTO);

        mockMvc.perform(post("/api/produits")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(produitDTO)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value("prod-1"))
                .andExpect(jsonPath("$.nom").value("Produit A"));

        verify(produitService).create(any(ProduitDTO.class));
    }

    @Test
    void shouldGetAllProduits() throws Exception {
        List<ProduitRespDTO> content = Arrays.asList(produitRespDTO);
        PageImpl<ProduitRespDTO> page = new PageImpl<>(content, PageRequest.of(0,10), content.size());
        when(produitService.getAll(any(Pageable.class))).thenReturn(page);

        mockMvc.perform(get("/api/produits")
                        .param("page","0")
                        .param("size","10"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content[0].id").value("prod-1"));

        verify(produitService).getAll(any(Pageable.class));
    }

    @Test
    void shouldUpdateProduit() throws Exception {
        ProduitRespDTO updated = new ProduitRespDTO();
        updated.setId("prod-1");
        updated.setNom("Produit B");
        updated.setPrix(BigDecimal.valueOf(15));
        when(produitService.update(eq("prod-1"), any(ProduitDTO.class))).thenReturn(updated);

        mockMvc.perform(put("/api/produits/{produitId}", "prod-1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(produitDTO)))
                .andExpect(status().isAccepted())
                .andExpect(jsonPath("$.nom").value("Produit B"));

        verify(produitService).update(eq("prod-1"), any(ProduitDTO.class));
    }

    @Test
    void shouldGetProduitById() throws Exception {
        when(produitService.getById(eq("prod-1"))).thenReturn(produitRespDTO);

        mockMvc.perform(get("/api/produits/{produitId}", "prod-1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value("prod-1"));

        verify(produitService).getById(eq("prod-1"));
    }

    @Test
    void shouldDeleteProduit() throws Exception {
        mockMvc.perform(delete("/api/produits/{produitId}", "prod-1"))
                .andExpect(status().isNoContent());

        verify(produitService).delete(eq("prod-1"));
    }
}