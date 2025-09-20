package com.etorres.banking.accounts.service;

import com.etorres.banking.accounts.dto.CuentaStatementDTO;

import java.time.LocalDate;

public interface ReporteService {
    CuentaStatementDTO generateStatement(String clientId, LocalDate startDate, LocalDate endDate);
}
