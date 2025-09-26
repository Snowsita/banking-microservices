package com.etorres.banking.accounts.service;

import com.etorres.banking.accounts.dto.MovementRequestDTO;
import com.etorres.banking.accounts.exception.SaldoInsuficienteException;
import com.etorres.banking.accounts.model.Account;
import com.etorres.banking.accounts.repository.AccountRepository;
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
public class MovementServiceImplTest {

    @Mock
    private AccountRepository accountRepository;

    @InjectMocks
    private MovementServiceImpl movimientoService;

    private Account testAccount;

    @BeforeEach
    void setUp() {
        testAccount = new Account();
        testAccount.setId(1L);
        testAccount.setAccountNumber("123456");
        testAccount.setCurrentBalance(new BigDecimal("100.00"));
    }

    /**
     * Test para verificar la creación de un movimiento con fondos suficientes.
     * Verifica que el balance de la cuenta se actualiza correctamente.
     */
    @Test
    void whenCreateMovement_withSufficientFunds_thenBalanceIsUpdated() {
        var request = new MovementRequestDTO("123456", new BigDecimal("-50.00"));

        when(accountRepository.findByAccountNumber("123456")).thenReturn(Optional.of(testAccount));
        when(accountRepository.save(any(Account.class))).thenReturn(testAccount);

        var responseDTO = movimientoService.create(request);

        assertNotNull(responseDTO);
        assertEquals(0, new BigDecimal("50.00").compareTo(responseDTO.balance()));

        verify(accountRepository, times(1)).save(any(Account.class));
    }

    /**
     * Test para verificar la creación de un movimiento con fondos insuficientes.
     * Verifica que se lanza una excepción de saldo insuficiente y que no se actualiza el balance.
     */
    @Test
    void whenCreateMovement_withInsufficientFunds_thenThrowException() {
        var request = new MovementRequestDTO("123456", new BigDecimal("-200.00"));

        when(accountRepository.findByAccountNumber("123456")).thenReturn(Optional.of(testAccount));

        assertThrows(SaldoInsuficienteException.class, () -> movimientoService.create(request));

        verify(accountRepository, never()).save(any(Account.class));
    }
}
