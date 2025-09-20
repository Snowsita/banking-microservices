package com.etorres.banking.accounts.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record MovimientoResponseDTO(
        Long id,
        LocalDateTime date,
        String movementType,
        BigDecimal value,
        BigDecimal balance
) {
}
