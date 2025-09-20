package com.etorres.banking.accounts.dto;

import java.math.BigDecimal;

public record MovimientoRequestDTO(
        String accountNumber,
        BigDecimal value
) {
}
