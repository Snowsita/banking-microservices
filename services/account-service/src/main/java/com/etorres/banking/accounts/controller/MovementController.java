package com.etorres.banking.accounts.controller;

import com.etorres.banking.accounts.dto.MovementRequestDTO;
import com.etorres.banking.accounts.dto.MovementResponseDTO;
import com.etorres.banking.accounts.service.MovementService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/movements")
public class MovementController {

    private static final Logger log = LoggerFactory.getLogger(MovementController.class);
    private final MovementService movementService;

    /**
     * Endpoint para crear un nuevo movimiento bancario.
     * HTTP Method: POST
     * URL: /api/v1/movimientos
     * Response Status: 201 Created
     * @param request DTO que contiene los datos necesarios para crear el movimiento.
     * @return DTO con los datos del movimiento creado.
     */
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public MovementResponseDTO createMovement(@RequestBody @Valid MovementRequestDTO request) {
        log.info("INFO: Creando nuevo movimiento para la cuenta: {}", request.accountNumber());
        MovementResponseDTO response = movementService.create(request);
        log.info("INFO: Movimiento creado exitosamente con ID: {}", response.id());
        return response;
    }
}
