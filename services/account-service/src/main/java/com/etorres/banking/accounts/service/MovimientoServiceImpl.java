package com.etorres.banking.accounts.service;

import com.etorres.banking.accounts.dto.MovimientoRequestDTO;
import com.etorres.banking.accounts.dto.MovimientoResponseDTO;
import com.etorres.banking.accounts.exception.SaldoInsuficienteException;
import com.etorres.banking.accounts.model.Cuenta;
import com.etorres.banking.accounts.model.Movimiento;
import com.etorres.banking.accounts.repository.CuentaRepository;
import com.etorres.banking.accounts.repository.MovimientoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class MovimientoServiceImpl implements MovimientoService {

    private final MovimientoRepository movimientoRepository;
    private final CuentaRepository cuentaRepository;
    private static final String DEBIT_TYPE = "DEBIT";
    private static final String CREDIT_TYPE = "CREDIT";

    @Override
    @Transactional
    public MovimientoResponseDTO create(MovimientoRequestDTO request) {
        Cuenta cuenta = cuentaRepository.findByAccountNumber(request.accountNumber()).orElseThrow(() -> new RuntimeException("Cuenta no encontrada: " + request.accountNumber()));

        BigDecimal currentBalance = cuenta.getCurrentBalance();
        BigDecimal movementValue = request.value();

        if (movementValue.compareTo(BigDecimal.ZERO) < 0 && currentBalance.add(movementValue).compareTo(BigDecimal.ZERO) < 0) {
            throw new SaldoInsuficienteException("Saldo insuficiente para el débito solicitado.");
        }

        BigDecimal newBalance = currentBalance.add(movementValue);
        cuenta.setCurrentBalance(newBalance);

        Movimiento newMovimiento = new Movimiento();
        newMovimiento.setAccount(cuenta);
        newMovimiento.setDate(LocalDateTime.now());
        newMovimiento.setValue(movementValue);
        newMovimiento.setBalance(newBalance);
        newMovimiento.setMovementType(movementValue.compareTo(BigDecimal.ZERO) > 0 ? CREDIT_TYPE : DEBIT_TYPE);

        cuenta.getMovements().add(newMovimiento);
        cuentaRepository.save(cuenta);

        return new MovimientoResponseDTO(
                newMovimiento.getId(),
                newMovimiento.getDate(),
                newMovimiento.getMovementType(),
                newMovimiento.getValue(),
                newMovimiento.getBalance()
        );
    }
}
