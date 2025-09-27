package com.etorres.banking.accounts.controller;

import com.etorres.banking.accounts.dto.MovementRequestDTO;
import com.etorres.banking.accounts.dto.MovementResponseDTO;
import com.etorres.banking.accounts.service.MovementService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/movements")
public class MovementController {

    private static final Logger log = LoggerFactory.getLogger(MovementController.class);
    private final MovementService movementService;

    /**
     * Endpoint para crear un nuevo movimiento bancario.
     * HTTP Method: POST
     * URL: /api/v1/movements
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

    /**
     * Endpoint para obtener los detalles de un movimiento bancario por su ID.
     * HTTP Method: GET
     * URL: /api/v1/movements/{id}
     * @param id El ID del movimiento a buscar.
     * @return DTO con los datos del movimiento si se encuentra, o 404 Not Found si no existe.
     */
    @GetMapping("/{id}")
    public ResponseEntity<MovementResponseDTO> getMovementById(@PathVariable Long id) {
        log.info("INFO: Buscando movimiento con ID: {}", id);
        return movementService.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    /**
     * Endpoint para obtener todos los movimientos asociados a una cuenta bancaria.
     * HTTP Method: GET
     * URL: /api/v1/movements/account/{accountNumber}
     * @param accountNumber El número de cuenta para la cual se buscan los movimientos.
     * @return Lista de DTOs con los datos de los movimientos asociados a la cuenta.
     */
    @GetMapping("/account/{accountNumber}")
    public List<MovementResponseDTO> getMovementsByAccountNumber(@PathVariable String accountNumber) {
        log.info("INFO: Buscando todos los movimientos para la cuenta: {}", accountNumber);
        return movementService.findAllByAccountNumber(accountNumber);
    }
}
