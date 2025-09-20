package com.etorres.banking.accounts.controller;

import com.etorres.banking.accounts.dto.MovimientoRequestDTO;
import com.etorres.banking.accounts.dto.MovimientoResponseDTO;
import com.etorres.banking.accounts.service.MovimientoService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/movimientos")
public class MovimientoController {

    private final MovimientoService movimientoService;

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
    public MovimientoResponseDTO createMovement(@RequestBody MovimientoRequestDTO request) {
        return movimientoService.create(request);
    }
}
