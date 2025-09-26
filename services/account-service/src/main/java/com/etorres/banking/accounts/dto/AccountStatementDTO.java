package com.etorres.banking.accounts.dto;

import java.math.BigDecimal;
import java.util.List;

public record AccountStatementDTO(
        String dateRange,
        String clientName,
        List<AccountDetail> accounts
) {
    public record AccountDetail(
            String accountNumber,
            String accountType,
            BigDecimal initialBalance,
            BigDecimal currentBalance,
            List<MovementResponseDTO> movements
    ) {
    }
}
