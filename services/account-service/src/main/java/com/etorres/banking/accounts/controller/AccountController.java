package com.etorres.banking.accounts.controller;

import com.etorres.banking.accounts.dto.AccountRequestDTO;
import com.etorres.banking.accounts.dto.AccountResponseDTO;
import com.etorres.banking.accounts.dto.AccountUpdateRequestDTO;
import com.etorres.banking.accounts.service.AccountService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/accounts")
public class AccountController {

    private final AccountService accountService;

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
    public AccountResponseDTO createAccount(@RequestBody AccountRequestDTO request) {
        return accountService.create(request);
    }

    /**
     * Endpoint para obtener los detalles de una cuenta bancaria por su número de cuenta.
     * HTTP Method: GET
     * URL: /api/v1/cuentas/{accountNumber}
     * @param accountNumber El número de cuenta a buscar.
     * @return DTO con los datos de la cuenta si se encuentra, o 404 Not Found si no existe.
     */
    @GetMapping("/{accountNumber}")
    public ResponseEntity<AccountResponseDTO> getAccountByNumber(@PathVariable String accountNumber) {
        return accountService.findByAccountNumber(accountNumber)
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
    public ResponseEntity<AccountResponseDTO> updateAccount(@PathVariable String accountNumber, @RequestBody AccountUpdateRequestDTO request) {
        return accountService.update(accountNumber, request)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound()
                        .build());
    }
}
