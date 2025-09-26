package com.etorres.banking.accounts.service;

import com.etorres.banking.accounts.client.ClientServiceFeignClient;
import com.etorres.banking.accounts.dto.AccountStatementDTO;
import com.etorres.banking.accounts.dto.MovementResponseDTO;
import com.etorres.banking.accounts.model.Account;
import com.etorres.banking.accounts.repository.AccountRepository;
import com.etorres.banking.accounts.repository.MovementRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ReportServiceImpl implements ReportService {

    private final AccountRepository accountRepository;
    private final MovementRepository movementRepository;
    private final ClientServiceFeignClient clientServiceFeignClient;

    @Override
    @Transactional(readOnly = true)
    public AccountStatementDTO generateStatement(String clientId, LocalDate startDate, LocalDate endDate) {
        List<Account> accounts = accountRepository.findByClientId(clientId);

        LocalDateTime startDateTime = startDate.atStartOfDay();
        LocalDateTime endDateTime = endDate.atTime(LocalTime.MAX);

        List<AccountStatementDTO.AccountDetail> accountDetails = accounts.stream().map(cuenta -> {
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

            return new AccountStatementDTO.AccountDetail(
                    cuenta.getAccountNumber(),
                    cuenta.getAccountType(),
                    cuenta.getInitialBalance(),
                    cuenta.getCurrentBalance(),
                    movements
            );
        }).toList();

        String dateRange = String.format("%s to %s", startDate, endDate);

        String clientName = clientServiceFeignClient.getClientById(clientId).name();

        return new AccountStatementDTO(dateRange, clientName, accountDetails);
    }
}
