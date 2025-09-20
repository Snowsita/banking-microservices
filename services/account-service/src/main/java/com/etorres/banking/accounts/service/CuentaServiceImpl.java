package com.etorres.banking.accounts.service;

import com.etorres.banking.accounts.dto.CuentaRequestDTO;
import com.etorres.banking.accounts.dto.CuentaResponseDTO;
import com.etorres.banking.accounts.model.Cuenta;
import com.etorres.banking.accounts.repository.CuentaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CuentaServiceImpl implements CuentaService {

    private final CuentaRepository cuentaRepository;

    @Override
    public CuentaResponseDTO create(CuentaRequestDTO request) {
        Cuenta newCuenta = new Cuenta();
        newCuenta.setAccountNumber(request.accountNumber());
        newCuenta.setAccountType(request.accountType());
        newCuenta.setInitialBalance(request.initialBalance());
        newCuenta.setCurrentBalance(request.initialBalance());
        newCuenta.setStatus(request.status());
        newCuenta.setClientId(request.clientId());

        Cuenta savedCuenta = cuentaRepository.save(newCuenta);

        return new CuentaResponseDTO(
                savedCuenta.getAccountNumber(),
                savedCuenta.getAccountType(),
                savedCuenta.getInitialBalance(),
                savedCuenta.getCurrentBalance(),
                savedCuenta.getStatus(),
                savedCuenta.getClientId()
        );
    }
}
