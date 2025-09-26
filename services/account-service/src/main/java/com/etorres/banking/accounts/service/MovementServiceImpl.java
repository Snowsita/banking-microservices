package com.etorres.banking.accounts.service;

import com.etorres.banking.accounts.dto.MovementRequestDTO;
import com.etorres.banking.accounts.dto.MovementResponseDTO;
import com.etorres.banking.accounts.exception.SaldoInsuficienteException;
import com.etorres.banking.accounts.model.Account;
import com.etorres.banking.accounts.model.Movement;
import com.etorres.banking.accounts.repository.AccountRepository;
import com.etorres.banking.accounts.repository.MovementRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class MovementServiceImpl implements MovementService {

    private final MovementRepository movementRepository;
    private final AccountRepository accountRepository;
    private static final String DEBIT_TYPE = "DEBIT";
    private static final String CREDIT_TYPE = "CREDIT";

    @Override
    @Transactional
    public MovementResponseDTO create(MovementRequestDTO request) {
        Account account = accountRepository.findByAccountNumber(request.accountNumber()).orElseThrow(() -> new RuntimeException("Cuenta no encontrada: " + request.accountNumber()));

        BigDecimal currentBalance = account.getCurrentBalance();
        BigDecimal movementValue = request.value();

        if (movementValue.compareTo(BigDecimal.ZERO) < 0 && currentBalance.add(movementValue).compareTo(BigDecimal.ZERO) < 0) {
            throw new SaldoInsuficienteException("Saldo insuficiente para el débito solicitado.");
        }

        BigDecimal newBalance = currentBalance.add(movementValue);
        account.setCurrentBalance(newBalance);

        Movement newMovement = new Movement();
        newMovement.setAccount(account);
        newMovement.setDate(LocalDateTime.now());
        newMovement.setValue(movementValue);
        newMovement.setBalance(newBalance);
        newMovement.setMovementType(movementValue.compareTo(BigDecimal.ZERO) > 0 ? CREDIT_TYPE : DEBIT_TYPE);

        account.getMovements().add(newMovement);
        accountRepository.save(account);

        return new MovementResponseDTO(
                newMovement.getId(),
                newMovement.getDate(),
                newMovement.getMovementType(),
                newMovement.getValue(),
                newMovement.getBalance()
        );
    }
}
