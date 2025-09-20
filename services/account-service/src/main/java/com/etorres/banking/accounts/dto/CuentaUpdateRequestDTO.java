package com.etorres.banking.accounts.dto;

public record CuentaUpdateRequestDTO(
        String accountType,
        Boolean status
) {
}
