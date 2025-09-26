package com.etorres.banking.accounts.service;

import com.etorres.banking.accounts.dto.AccountRequestDTO;
import com.etorres.banking.accounts.dto.AccountResponseDTO;
import com.etorres.banking.accounts.dto.AccountUpdateRequestDTO;
import com.etorres.banking.accounts.mapper.AccountMapper;
import com.etorres.banking.accounts.model.Account;
import com.etorres.banking.accounts.repository.AccountRepository;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AccountServiceImpl implements AccountService {

    private static final Logger log = LoggerFactory.getLogger(AccountServiceImpl.class);
    private final AccountRepository accountRepository;
    private final AccountMapper accountMapper;

    @Override
    @Transactional
    public AccountResponseDTO create(AccountRequestDTO request) {
        log.info("INFO: Creando cuenta nueva con número: {}", request.accountNumber());
        Account newAccount = accountMapper.toEntity(request);

        Account savedAccount = accountRepository.save(newAccount);
        log.info("INFO: Cuenta guardada con ID: {}", savedAccount.getId());

        return accountMapper.toDto(savedAccount);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<AccountResponseDTO> findByAccountNumber(String accountNumber) {
        log.info("INFO: Buscando cuenta por número: {}", accountNumber);
        Optional<AccountResponseDTO> accountDTO = accountRepository.findByAccountNumber(accountNumber)
                .map(accountMapper::toDto);
        if (accountDTO.isPresent()) {
            log.info("INFO: Cuenta encontrada con número: {}", accountNumber);
        } else {
            log.warn("WARN: Cuenta no encontrada con número: {}", accountNumber);
        }
        return accountDTO;
    }

    @Override
    @Transactional
    public Optional<AccountResponseDTO> update(String accountNumber, AccountUpdateRequestDTO request) {
        log.info("INFO: Actualizando cuenta con número: {}", accountNumber);
        return accountRepository.findByAccountNumber(accountNumber).map(account -> {
            log.info("INFO: Cuenta encontrada para actualizar. Número: {}", accountNumber);
            accountMapper.updateEntityFromDto(request, account);

            Account updatedAccount = accountRepository.save(account);
            log.info("INFO: Cuenta actualizada con ID: {}", updatedAccount.getId());

            return accountMapper.toDto(updatedAccount);
        });
    }

}
