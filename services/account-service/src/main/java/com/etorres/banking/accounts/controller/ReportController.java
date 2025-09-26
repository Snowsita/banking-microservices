package com.etorres.banking.accounts.controller;

import com.etorres.banking.accounts.dto.AccountStatementDTO;
import com.etorres.banking.accounts.service.ReportService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/reports")
public class ReportController {

    private static final Logger log = LoggerFactory.getLogger(ReportController.class);
    private final ReportService reportService;

    /**
     * Genera un estado de cuenta para un cliente en un rango de fechas específico.
     * HTTP Method: GET
     * URL: /api/v1/reports?clientId={clientId}&startDate={startDate}&endDate={endDate}
     * HTTP Status: 200 OK
     * @param clientId Identificador del cliente
     * @param startDate Fecha de inicio en formato ISO (yyyy-MM-dd)
     * @param endDate Fecha de fin en formato ISO (yyyy-MM-dd)
     * @return CuentaStatementDTO con el estado de cuenta del cliente
     */
    @GetMapping
    public AccountStatementDTO getAccountStatement(
            @RequestParam String clientId,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate
    ) {
        log.info("INFO: Generando estado de cuenta para el cliente ID: {} desde {} hasta {}", clientId, startDate, endDate);
        AccountStatementDTO statement = reportService.generateStatement(clientId, startDate, endDate);
        log.info("INFO: Estado de cuenta generado exitosamente para el cliente ID: {}", clientId);
        return statement;
    }
}
