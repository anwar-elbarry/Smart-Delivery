package org.example.smart_delivery.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.example.smart_delivery.dto.request.ColisDTO;
import org.example.smart_delivery.dto.request.UserDTO;
import org.example.smart_delivery.dto.request.ZoneDTO;
import org.example.smart_delivery.dto.response.ColisRespDTO;
import org.example.smart_delivery.entity.enums.Priority;
import org.example.smart_delivery.entity.enums.UserRole;
import org.example.smart_delivery.service.colis.ColisService;
import org.example.smart_delivery.service.colis.Coliscounter;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.List;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(ColisController.class)
class ColisControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockitoBean
    private ColisService colisService;

    private UserDTO clientExpedeteur;
    private UserDTO clientDestinataire;
    private ColisRespDTO colisRespDTO;
    private ColisDTO colisDTO;
    private ZoneDTO zoneDTO;

    @BeforeEach
    void setup() {
        this.clientExpedeteur = UserDTO.builder()
                .id("exp-123")
                .nom("exp")
                .prenom("expPre")
                .adress("marr")
                .email("exp@example.com")
                .role(UserRole.EXPEDITEUR)
                .telephone("087658733")
                .build();
        this.clientDestinataire = UserDTO.builder()
                .id("dest-123")
                .nom("dest")
                .prenom("destPre")
                .adress("marr")
                .email("dest@example.com")
                .role(UserRole.DESTINATAIRE)
                .telephone("087658733")
                .build();
        this.zoneDTO = new ZoneDTO();
        this.zoneDTO.setId("zone-123");
        this.zoneDTO.setNome("marrakech");

        this.colisRespDTO = ColisRespDTO.builder()
                .id("colisId")
                .description("desc")
                .priorite(Priority.MEDIUM)
                .poids(BigDecimal.valueOf(23))
                .clientExpediteur(clientExpedeteur)
                .destinataire(clientDestinataire)
                .zone(zoneDTO)
                .villeDestination("ville")
                .build();

        this.colisDTO = ColisDTO.builder()
                .id("colisId")
                .description("desc")
                .priorite(Priority.MEDIUM)
                .poids(BigDecimal.valueOf(23))
                .clientExpediteurId("exp-123")
                .destinataireId("dest-123")
                .zoneId("zone-123")
                .villeDestination("ville")
                .build();
    }

    @Test
    void shouldCreateColisSuccessfully() throws Exception {

        // When - accept any ColisDTO because MVC deserialise en une autre instance
        when(colisService.create(any(ColisDTO.class))).thenReturn(colisRespDTO);

        // Expect
        mockMvc.perform(post("/api/colis")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(colisDTO))
                )
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value("colisId"))
                .andExpect(jsonPath("$.description").value("desc"));

        // Verify service called with any ColisDTO (not the exact instance)
        verify(colisService).create(any(ColisDTO.class));
    }

    @Test
    void shouldUpdateColisSuccessfully() throws Exception {
        String colisId = "colisId";
        ColisRespDTO updated = ColisRespDTO.builder()
                .id(colisId)
                .description("updated")
                .build();

        when(colisService.update(eq(colisId), any(ColisDTO.class))).thenReturn(updated);

        mockMvc.perform(put("/api/colis/{coliId}", colisId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(colisDTO)))
                .andExpect(status().isAccepted())
                .andExpect(jsonPath("$.id").value(colisId))
                .andExpect(jsonPath("$.description").value("updated"));

        verify(colisService).update(eq(colisId), any(ColisDTO.class));
    }

    @Test
    void shouldAssignColisSuccessfully() throws Exception {
        String colisId = "colisId";
        String livreurId = "liv-1";

        doNothing().when(colisService).Assign_col(anyString(), anyString());

        mockMvc.perform(put("/api/colis/assign")
                        .param("colisId", colisId)
                        .param("livreurId", livreurId))
                .andExpect(status().isNoContent());

        verify(colisService).Assign_col(eq(colisId), eq(livreurId));
    }

    @Test
    void shouldGetAllColis() throws Exception {
        List<ColisRespDTO> content = Collections.singletonList(colisRespDTO);
        PageImpl<ColisRespDTO> page = new PageImpl<>(content, PageRequest.of(0, 10), content.size());

        when(colisService.getAll(any(Pageable.class))).thenReturn(page);

        mockMvc.perform(get("/api/colis")
                        .param("page", "0")
                        .param("size", "10")
                        .param("sortBy", "id")
                        .param("sortDir", "asc"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content[0].id").value("colisId"));

        verify(colisService).getAll(any(Pageable.class));
    }

    @Test
    void shouldCreateColisRequest() throws Exception {
        String expedId = "exp-123";
        String distenId = "dest-123";

        doNothing().when(colisService).createColisRequest(anyString(), anyString(), any());

        mockMvc.perform(put("/api/colis/colisRequest")
                        .param("expedId", expedId)
                        .param("distenId", distenId)
                        .param("produitIds", "p1")
                        .param("produitIds", "p2"))
                .andExpect(status().isNoContent());

        verify(colisService).createColisRequest(eq(expedId), eq(distenId), any());
    }

    @Test
    void shouldSearchColis() throws Exception {
        String q = "desc";
        List<ColisRespDTO> content = Collections.singletonList(colisRespDTO);
        PageImpl<ColisRespDTO> page = new PageImpl<>(content, PageRequest.of(0, 10), content.size());

        when(colisService.search(anyString(), any(Pageable.class))).thenReturn(page);

        mockMvc.perform(get("/api/colis/search")
                        .param("q", q)
                        .param("page", "0")
                        .param("size", "10"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content[0].id").value("colisId"));

        verify(colisService).search(eq(q), any(Pageable.class));
    }

    @Test
    void shouldCalculateCounter() throws Exception {
        String livreurId = "liv-1";
        Coliscounter counter = Coliscounter.builder().count(5L).poids(BigDecimal.valueOf(100)).build();

        when(colisService.calcule(anyString())).thenReturn(counter);

        mockMvc.perform(get("/api/colis/calcule")
                        .param("livreurId", livreurId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.count").value(5))
                .andExpect(jsonPath("$.poids").value(100));

        verify(colisService).calcule(eq(livreurId));
    }
}