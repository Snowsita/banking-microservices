package com.etorres.banking.accounts.dto;

import java.math.BigDecimal;

public record AccountResponseDTO(
        Long id,
        String accountNumber,
        String accountType,
        BigDecimal initialBalance,
        BigDecimal currentBalance,
        Boolean status,
        String clientId
) {
}
