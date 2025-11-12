package org.example.smart_delivery.controller;

import org.example.smart_delivery.service.colis.ColisService;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.junit.jupiter.api.Assertions.*;

@WebMvcTest
class ColisControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private ColisService colisService;



    @BeforeEach
    void setUp() {
    }
}