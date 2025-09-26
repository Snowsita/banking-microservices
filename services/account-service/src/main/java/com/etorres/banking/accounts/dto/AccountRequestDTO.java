package com.etorres.banking.accounts.dto;

import java.math.BigDecimal;

public record AccountRequestDTO(
        String accountNumber,
        String accountType,
        BigDecimal initialBalance,
        String clientId,
        Boolean status
) {
}
