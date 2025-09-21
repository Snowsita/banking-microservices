package com.etorres.banking.accounts.service;

import com.etorres.banking.accounts.client.ClientServiceFeignClient;
import com.etorres.banking.accounts.dto.CuentaStatementDTO;
import com.etorres.banking.accounts.dto.MovimientoResponseDTO;
import com.etorres.banking.accounts.model.Cuenta;
import com.etorres.banking.accounts.repository.CuentaRepository;
import com.etorres.banking.accounts.repository.MovimientoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ReporteServiceImpl implements ReporteService {

    private final CuentaRepository cuentaRepository;
    private final MovimientoRepository movimientoRepository;
    private final ClientServiceFeignClient clientServiceFeignClient;

    @Override
    @Transactional(readOnly = true)
    public CuentaStatementDTO generateStatement(String clientId, LocalDate startDate, LocalDate endDate) {
        List<Cuenta> cuentas = cuentaRepository.findByClientId(clientId);

        LocalDateTime startDateTime = startDate.atStartOfDay();
        LocalDateTime endDateTime = endDate.atTime(LocalTime.MAX);

        List<CuentaStatementDTO.AccountDetail> accountDetails = cuentas.stream().map(cuenta -> {
            List<MovimientoResponseDTO> movements = movimientoRepository
                    .findByAccount_ClientIdAndDateBetweenOrderByDateAsc(clientId, startDateTime, endDateTime)
                    .stream()
                    .filter(movimiento -> movimiento.getAccount().getId().equals(cuenta.getId()))
                    .map(movimiento -> new MovimientoResponseDTO(
                            movimiento.getId(),
                            movimiento.getDate(),
                            movimiento.getMovementType(),
                            movimiento.getValue(),
                            movimiento.getBalance()
                    ))
                    .toList();

            return new CuentaStatementDTO.AccountDetail(
                    cuenta.getAccountNumber(),
                    cuenta.getAccountType(),
                    cuenta.getInitialBalance(),
                    cuenta.getCurrentBalance(),
                    movements
            );
        }).toList();

        String dateRange = String.format("%s to %s", startDate, endDate);

        String clientName = clientServiceFeignClient.getClientById(clientId).name();

        return new CuentaStatementDTO(dateRange, clientName, accountDetails);
    }
}
