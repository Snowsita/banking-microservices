package com.etorres.banking.accounts.service;

import com.etorres.banking.accounts.dto.MovimientoRequestDTO;
import com.etorres.banking.accounts.exception.SaldoInsuficienteException;
import com.etorres.banking.accounts.model.Cuenta;
import com.etorres.banking.accounts.repository.CuentaRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class MovimientoServiceImplTest {

    @Mock
    private CuentaRepository cuentaRepository;

    @InjectMocks
    private MovimientoServiceImpl movimientoService;

    private Cuenta testAccount;

    @BeforeEach
    void setUp() {
        testAccount = new Cuenta();
        testAccount.setId(1L);
        testAccount.setAccountNumber("123456");
        testAccount.setCurrentBalance(new BigDecimal("100.00"));
    }

    @Test
    void whenCreateMovement_withSufficientFunds_thenBalanceIsUpdated() {
        var request = new MovimientoRequestDTO("123456", new BigDecimal("-50.00"));

        when(cuentaRepository.findByAccountNumber("123456")).thenReturn(Optional.of(testAccount));
        when(cuentaRepository.save(any(Cuenta.class))).thenReturn(testAccount);

        var responseDTO = movimientoService.create(request);

        assertNotNull(responseDTO);
        assertEquals(0, new BigDecimal("50.00").compareTo(responseDTO.balance()));

        verify(cuentaRepository, times(1)).save(any(Cuenta.class));
    }

    @Test
    void whenCreateMovement_withInsufficientFunds_thenThrowException() {
        var request = new MovimientoRequestDTO("123456", new BigDecimal("-200.00"));

        when(cuentaRepository.findByAccountNumber("123456")).thenReturn(Optional.of(testAccount));

        assertThrows(SaldoInsuficienteException.class, () -> {
            movimientoService.create(request);
        });

        verify(cuentaRepository, never()).save(any(Cuenta.class));
    }
}
