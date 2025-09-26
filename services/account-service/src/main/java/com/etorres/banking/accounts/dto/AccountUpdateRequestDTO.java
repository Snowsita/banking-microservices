package com.etorres.banking.accounts.dto;

import jakarta.validation.constraints.NotBlank;

public record AccountUpdateRequestDTO(
        @NotBlank(message = "El tipo de cuenta no puede estar vacío")
        String accountType,
        Boolean status
) {
}
