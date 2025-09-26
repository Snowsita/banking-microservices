package com.etorres.banking.accounts.service;

import com.etorres.banking.accounts.dto.AccountStatementDTO;

import java.time.LocalDate;

public interface ReportService {
    AccountStatementDTO generateStatement(String clientId, LocalDate startDate, LocalDate endDate);
}
