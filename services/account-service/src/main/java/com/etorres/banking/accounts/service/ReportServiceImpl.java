package com.etorres.banking.accounts.service;

import com.etorres.banking.accounts.client.ClientServiceFeignClient;
import com.etorres.banking.accounts.dto.AccountStatementDTO;
import com.etorres.banking.accounts.dto.MovementResponseDTO;
import com.etorres.banking.accounts.model.Account;
import com.etorres.banking.accounts.repository.AccountRepository;
import com.etorres.banking.accounts.repository.MovementRepository;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ReportServiceImpl implements ReportService {

    private static final Logger log = LoggerFactory.getLogger(ReportServiceImpl.class);
    private final AccountRepository accountRepository;
    private final MovementRepository movementRepository;
    private final ClientServiceFeignClient clientServiceFeignClient;

    @Override
    @Transactional(readOnly = true)
    public AccountStatementDTO generateStatement(String clientId, LocalDate startDate, LocalDate endDate) {
        log.info("INFO: Generando estado de cuenta para el cliente ID: {} en el rango de fechas: {} a {}", clientId, startDate, endDate);
        List<Account> accounts = accountRepository.findByClientId(clientId);
        log.info("INFO: Se encontraron {} cuentas para el cliente ID: {}", accounts.size(), clientId);

        LocalDateTime startDateTime = startDate.atStartOfDay();
        LocalDateTime endDateTime = endDate.atTime(LocalTime.MAX);

        List<AccountStatementDTO.AccountDetail> accountDetails = accounts.stream().map(cuenta -> {
            log.info("INFO: Procesando cuenta con número: {}", cuenta.getAccountNumber());
            List<MovementResponseDTO> movements = movementRepository
                    .findByAccount_ClientIdAndDateBetweenOrderByDateAsc(clientId, startDateTime, endDateTime)
                    .stream()
                    .filter(movimiento -> movimiento.getAccount().getId().equals(cuenta.getId()))
                    .map(movimiento -> new MovementResponseDTO(
                            movimiento.getId(),
                            movimiento.getDate(),
                            movimiento.getMovementType(),
                            movimiento.getValue(),
                            movimiento.getBalance()
                    ))
                    .toList();
            log.info("INFO: Se encontraron {} movimientos para la cuenta: {}", movements.size(), cuenta.getAccountNumber());

            return new AccountStatementDTO.AccountDetail(
                    cuenta.getAccountNumber(),
                    cuenta.getAccountType(),
                    cuenta.getInitialBalance(),
                    cuenta.getCurrentBalance(),
                    movements
            );
        }).toList();

        String dateRange = String.format("%s to %s", startDate, endDate);

        log.info("INFO: Obteniendo nombre del cliente con ID: {}", clientId);
        String clientName = clientServiceFeignClient.getClientById(clientId).name();
        log.info("INFO: Nombre del cliente obtenido: {}", clientName);

        return new AccountStatementDTO(dateRange, clientName, accountDetails);
    }
}
