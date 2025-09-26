package com.etorres.banking.accounts.controller;

import com.etorres.banking.accounts.dto.AccountRequestDTO;
import com.etorres.banking.accounts.dto.AccountResponseDTO;
import com.etorres.banking.accounts.dto.AccountUpdateRequestDTO;
import com.etorres.banking.accounts.service.AccountService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/accounts")
public class AccountController {

    private static final Logger log = LoggerFactory.getLogger(AccountController.class);
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
    public AccountResponseDTO createAccount(@RequestBody @Valid AccountRequestDTO request) {
        log.info("INFO: Creando nueva cuenta para el cliente ID: {}", request.clientId());
        AccountResponseDTO response = accountService.create(request);
        log.info("INFO: Cuenta creada exitosamente con número: {}", response.accountNumber());
        return response;
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
        log.info("INFO: Buscando cuenta por número: {}", accountNumber);
        return accountService.findByAccountNumber(accountNumber)
                .map(account -> {
                    log.info("INFO: Cuenta encontrada: {}", account.accountNumber());
                    return ResponseEntity.ok(account);
                })
                .orElseGet(() -> {
                    log.warn("WARN: Cuenta no encontrada con número: {}", accountNumber);
                    return ResponseEntity.notFound().build();
                });
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
    public ResponseEntity<AccountResponseDTO> updateAccount(@PathVariable String accountNumber, @RequestBody @Valid AccountUpdateRequestDTO request) {
        log.info("INFO: Actualizando cuenta con número: {}", accountNumber);
        return accountService.update(accountNumber, request)
                .map(account -> {
                    log.info("INFO: Cuenta actualizada exitosamente: {}", account.accountType());
                    return ResponseEntity.ok(account);
                })
                .orElseGet(() -> {
                    log.warn("WARN: Cuenta no encontrada para actualizar con número: {}", accountNumber);
                    return ResponseEntity.notFound().build();
                });
    }
}
