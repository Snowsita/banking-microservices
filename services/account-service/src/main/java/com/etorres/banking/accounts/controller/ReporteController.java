package com.etorres.banking.accounts.controller;

import com.etorres.banking.accounts.dto.CuentaStatementDTO;
import com.etorres.banking.accounts.service.ReporteService;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/reports")
public class ReporteController {

    private final ReporteService reporteService;

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
    public CuentaStatementDTO getAccountStatement(
            @RequestParam String clientId,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate
    ) {
        return reporteService.generateStatement(clientId, startDate, endDate);
    }
}
