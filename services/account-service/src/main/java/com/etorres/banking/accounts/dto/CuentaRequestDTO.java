package com.etorres.banking.accounts.dto;

import java.math.BigDecimal;

public record CuentaRequestDTO(
        String accountNumber,
        String accountType,
        BigDecimal initialBalance,
        String clientId,
        Boolean status
) {
}
