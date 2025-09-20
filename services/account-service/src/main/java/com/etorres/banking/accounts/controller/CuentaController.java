package com.etorres.banking.accounts.controller;

import com.etorres.banking.accounts.dto.CuentaRequestDTO;
import com.etorres.banking.accounts.dto.CuentaResponseDTO;
import com.etorres.banking.accounts.dto.CuentaUpdateRequestDTO;
import com.etorres.banking.accounts.service.CuentaService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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

    /**
     * Endpoint para obtener los detalles de una cuenta bancaria por su número de cuenta.
     * HTTP Method: GET
     * URL: /api/v1/cuentas/{accountNumber}
     * @param accountNumber El número de cuenta a buscar.
     * @return DTO con los datos de la cuenta si se encuentra, o 404 Not Found si no existe.
     */
    @GetMapping("/{accountNumber}")
    public ResponseEntity<CuentaResponseDTO> getAccountByNumber(@PathVariable String accountNumber) {
        return cuentaService.findByAccountNumber(accountNumber)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    /**
     * Endpoint para actualizar los detalles de una cuenta bancaria existente.
     * HTTP Method: PUT
     * URL: /api/v1/cuentas/{accountNumber}
     * @param accountNumber El número de cuenta de la cuenta a actualizar.
     * @param request DTO que contiene los nuevos datos para la cuenta.
     * @return DTO con los datos actualizados de la cuenta si se encuentra, o 404 Not Found si no existe.
     */
    @PutMapping("/{accountNumber}")
    public ResponseEntity<CuentaResponseDTO> updateAccount(@PathVariable String accountNumber, @RequestBody CuentaUpdateRequestDTO request) {
        return cuentaService.update(accountNumber, request)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound()
                        .build());
    }
}
