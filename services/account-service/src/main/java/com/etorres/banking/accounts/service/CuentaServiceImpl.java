package com.etorres.banking.accounts.service;

import com.etorres.banking.accounts.dto.CuentaRequestDTO;
import com.etorres.banking.accounts.dto.CuentaResponseDTO;
import com.etorres.banking.accounts.dto.CuentaUpdateRequestDTO;
import com.etorres.banking.accounts.model.Cuenta;
import com.etorres.banking.accounts.repository.CuentaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CuentaServiceImpl implements CuentaService {

    private final CuentaRepository cuentaRepository;

    @Override
    @Transactional
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
                savedCuenta.getId(),
                savedCuenta.getAccountNumber(),
                savedCuenta.getAccountType(),
                savedCuenta.getInitialBalance(),
                savedCuenta.getCurrentBalance(),
                savedCuenta.getStatus(),
                savedCuenta.getClientId()
        );
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<CuentaResponseDTO> findByAccountNumber(String accountNumber) {
        return cuentaRepository.findByAccountNumber(accountNumber)
                .map(cuenta -> new CuentaResponseDTO(
                        cuenta.getId(),
                        cuenta.getAccountNumber(),
                        cuenta.getAccountType(),
                        cuenta.getInitialBalance(),
                        cuenta.getCurrentBalance(),
                        cuenta.getStatus(),
                        cuenta.getClientId()
                ));
    }

    @Override
    @Transactional
    public Optional<CuentaResponseDTO> update(String accountNumber, CuentaUpdateRequestDTO request) {
        return cuentaRepository.findByAccountNumber(accountNumber).map(cuenta -> {
            cuenta.setAccountType(request.accountType());
            cuenta.setStatus(request.status());

            Cuenta updatedCuenta = cuentaRepository.save(cuenta);

            return new CuentaResponseDTO(
                    updatedCuenta.getId(),
                    updatedCuenta.getAccountNumber(),
                    updatedCuenta.getAccountType(),
                    updatedCuenta.getInitialBalance(),
                    updatedCuenta.getCurrentBalance(),
                    updatedCuenta.getStatus(),
                    updatedCuenta.getClientId()
            );
        });
    }

}
