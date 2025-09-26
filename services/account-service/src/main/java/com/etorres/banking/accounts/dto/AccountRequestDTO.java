package com.etorres.banking.accounts.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import java.math.BigDecimal;

public record AccountRequestDTO(
        @NotBlank(message = "El número de cuenta es obligatorio")
        String accountNumber,
        @NotBlank(message = "El tipo de cuenta es obligatorio")
        String accountType,
        @NotNull(message = "El saldo inicial es obligatorio")
        @Positive(message = "El saldo inicial debe ser un valor positivo")
        BigDecimal initialBalance,
        @NotBlank(message = "El ID del cliente es obligatorio")
        String clientId,
        @NotNull(message = "El estado es obligatorio")
        Boolean status
) {
}
