package com.etorres.banking.accounts.controller;

import com.etorres.banking.accounts.dto.CuentaRequestDTO;
import com.etorres.banking.accounts.dto.CuentaResponseDTO;
import com.etorres.banking.accounts.service.CuentaService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/cuentas")
public class CuentaController {

    private final CuentaService cuentaService;

    /**
     * Endpoint para crear una nueva cuenta bancaria.
     * HTTP Method: POST
     * URL: /api/v1/cuentas
     * Response Status: 201 Created
     * @param request DTO que contiene los datos necesarios para crear la cuenta.
     * @return DTO con los datos de la cuenta creada.
     */
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public CuentaResponseDTO createAccount(@RequestBody CuentaRequestDTO request) {
        return cuentaService.create(request);
    }
}
