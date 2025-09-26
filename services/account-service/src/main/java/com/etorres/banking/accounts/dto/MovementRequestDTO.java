package com.etorres.banking.accounts.dto;

import java.math.BigDecimal;

public record MovementRequestDTO(
        String accountNumber,
        BigDecimal value
) {
}
