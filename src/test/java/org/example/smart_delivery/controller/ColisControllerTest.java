package org.example.smart_delivery.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.example.smart_delivery.dto.request.ColisDTO;
import org.example.smart_delivery.dto.response.ColisRespDTO;
import org.example.smart_delivery.entity.enums.ColisStatus;
import org.example.smart_delivery.entity.enums.Priority;
import org.example.smart_delivery.exception.GlobalException;
import org.example.smart_delivery.service.colis.ColisService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;
import java.util.UUID;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
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



    @Test
   void shouldCreateColisSuccessfully() throws Exception {

        // Given
        String colisId = UUID.randomUUID().toString();
        ColisRespDTO colisRespDTO = ColisRespDTO.builder()
                .id(colisId)
                .description("desc")
                .priorite(Priority.MEDIUM)
                .poids(BigDecimal.valueOf(23))
                .build();

        ColisDTO colisDTO = ColisDTO.builder()
                .id(colisId)
                .description("desc")
                .priorite(Priority.MEDIUM)
                .poids(BigDecimal.valueOf(23))
                .clientExpediteurId("expe-123")
                .destinataireId("dest-123")
                .zoneId("zone-123")
                .villeDestination("")
                .build();

        // When
        when(colisService.create(colisDTO)).thenReturn(colisRespDTO);

        // Expect
        mockMvc.perform(post("/api/colis")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(colisDTO))
                )
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(colisId))
                .andExpect(jsonPath("$.description").value("desc"));

        verify(colisService).create(colisDTO);
   }
}