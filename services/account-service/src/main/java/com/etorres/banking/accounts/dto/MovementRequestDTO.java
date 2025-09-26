package com.etorres.banking.accounts.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.math.BigDecimal;

public record MovementRequestDTO(
        @NotBlank(message = "El número de cuenta no puede estar vacío")
        String accountNumber,
        @NotNull(message = "El valor del movimiento no puede ser nulo")
        @Positive(message = "El valor del movimiento debe ser positivo")
        BigDecimal value
) {
}
