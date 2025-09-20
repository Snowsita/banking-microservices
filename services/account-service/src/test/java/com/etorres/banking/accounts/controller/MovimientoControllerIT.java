package com.etorres.banking.accounts.controller;

import com.etorres.banking.accounts.dto.MovimientoRequestDTO;
import com.etorres.banking.accounts.model.Cuenta;
import com.etorres.banking.accounts.repository.CuentaRepository;
import com.etorres.banking.accounts.repository.MovimientoRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.math.BigDecimal;

import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.httpBasic;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
@Testcontainers
public class MovimientoControllerIT {

    @Container
    static PostgreSQLContainer<?> postgres = new PostgreSQLContainer<>("postgres:15-alpine");

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private CuentaRepository cuentaRepository;

    @Autowired
    private MovimientoRepository movimientoRepository;

    @DynamicPropertySource
    static void configureProperties(DynamicPropertyRegistry registry) {
        registry.add("spring.datasource.url", postgres::getJdbcUrl);
        registry.add("spring.datasource.username", postgres::getUsername);
        registry.add("spring.datasource.password", postgres::getPassword);
    }

    @BeforeEach
    void setUp() {
        movimientoRepository.deleteAll();
        cuentaRepository.deleteAll();

        Cuenta testAccount = new Cuenta();
        testAccount.setAccountNumber("123456");
        testAccount.setAccountType("Corriente");
        testAccount.setInitialBalance(new BigDecimal("1000.00"));
        testAccount.setCurrentBalance(new BigDecimal("1000.00"));
        testAccount.setStatus(true);
        testAccount.setClientId("C-IT-002");
        cuentaRepository.save(testAccount);
    }

    @Test
    void whenPostMovement_thenCreatesMovementAndUpdatesBalance() throws  Exception {
        var request = new MovimientoRequestDTO("123456", new BigDecimal("250.00"));

        mockMvc.perform(post("/api/v1/movements")
                        .with(httpBasic("admin", "password"))
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.value", is(250.00)))
                .andExpect(jsonPath("$.balance", is(1250.00)));

        assertEquals(1, movimientoRepository.count());

        Cuenta updatedAccount = cuentaRepository.findByAccountNumber("123456").get();
        assertEquals(0, new BigDecimal("1250.00").compareTo(updatedAccount.getCurrentBalance()));
    }
}
