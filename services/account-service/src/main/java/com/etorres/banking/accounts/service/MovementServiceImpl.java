package com.etorres.banking.accounts.service;

import com.etorres.banking.accounts.dto.MovementRequestDTO;
import com.etorres.banking.accounts.dto.MovementResponseDTO;
import com.etorres.banking.accounts.exception.SaldoInsuficienteException;
import com.etorres.banking.accounts.model.Account;
import com.etorres.banking.accounts.model.Movement;
import com.etorres.banking.accounts.repository.AccountRepository;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class MovementServiceImpl implements MovementService {

    private static final Logger log = LoggerFactory.getLogger(MovementServiceImpl.class);
    private final AccountRepository accountRepository;
    private static final String DEBIT_TYPE = "DEBIT";
    private static final String CREDIT_TYPE = "CREDIT";

    @Override
    @Transactional
    public MovementResponseDTO create(MovementRequestDTO request) {
        log.info("INFO: Creando movimiento para la cuenta: {}", request.accountNumber());
        Account account = accountRepository.findByAccountNumber(request.accountNumber()).orElseThrow(() -> {
            log.error("ERROR: Cuenta no encontrada: {}", request.accountNumber());
            return new RuntimeException("Cuenta no encontrada: " + request.accountNumber());
        });

        BigDecimal currentBalance = account.getCurrentBalance();
        BigDecimal movementValue = request.value();

        log.info("INFO: Saldo actual: {}, Valor del movimiento: {}", currentBalance, movementValue);

        if (movementValue.compareTo(BigDecimal.ZERO) < 0 && currentBalance.add(movementValue).compareTo(BigDecimal.ZERO) < 0) {
            log.warn("WARN: Saldo insuficiente para el débito solicitado en la cuenta: {}", request.accountNumber());
            throw new SaldoInsuficienteException("Saldo insuficiente para el débito solicitado.");
        }

        BigDecimal newBalance = currentBalance.add(movementValue);
        account.setCurrentBalance(newBalance);
        log.info("INFO: Nuevo saldo: {}", newBalance);

        Movement newMovement = new Movement();
        newMovement.setAccount(account);
        newMovement.setDate(LocalDateTime.now());
        newMovement.setValue(movementValue);
        newMovement.setBalance(newBalance);
        newMovement.setMovementType(movementValue.compareTo(BigDecimal.ZERO) > 0 ? CREDIT_TYPE : DEBIT_TYPE);

        account.getMovements().add(newMovement);
        accountRepository.save(account);
        log.info("INFO: Movimiento guardado con ID: {}", newMovement.getId());

        return new MovementResponseDTO(
                newMovement.getId(),
                newMovement.getDate(),
                newMovement.getMovementType(),
                newMovement.getValue(),
                newMovement.getBalance()
        );
    }
}
