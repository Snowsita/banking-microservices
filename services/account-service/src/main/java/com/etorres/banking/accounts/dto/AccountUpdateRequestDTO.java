package com.etorres.banking.accounts.dto;

public record AccountUpdateRequestDTO(
        String accountType,
        Boolean status
) {
}
