package com.etorres.banking.accounts.service;

import com.etorres.banking.accounts.dto.AccountRequestDTO;
import com.etorres.banking.accounts.dto.AccountResponseDTO;
import com.etorres.banking.accounts.dto.AccountUpdateRequestDTO;
import com.etorres.banking.accounts.model.Account;
import com.etorres.banking.accounts.repository.AccountRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AccountServiceImpl implements AccountService {

    private final AccountRepository accountRepository;

    @Override
    @Transactional
    public AccountResponseDTO create(AccountRequestDTO request) {
        Account newAccount = new Account();
        newAccount.setAccountNumber(request.accountNumber());
        newAccount.setAccountType(request.accountType());
        newAccount.setInitialBalance(request.initialBalance());
        newAccount.setCurrentBalance(request.initialBalance());
        newAccount.setStatus(request.status());
        newAccount.setClientId(request.clientId());

        Account savedAccount = accountRepository.save(newAccount);

        return new AccountResponseDTO(
                savedAccount.getId(),
                savedAccount.getAccountNumber(),
                savedAccount.getAccountType(),
                savedAccount.getInitialBalance(),
                savedAccount.getCurrentBalance(),
                savedAccount.getStatus(),
                savedAccount.getClientId()
        );
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<AccountResponseDTO> findByAccountNumber(String accountNumber) {
        return accountRepository.findByAccountNumber(accountNumber)
                .map(cuenta -> new AccountResponseDTO(
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
    public Optional<AccountResponseDTO> update(String accountNumber, AccountUpdateRequestDTO request) {
        return accountRepository.findByAccountNumber(accountNumber).map(cuenta -> {
            cuenta.setAccountType(request.accountType());
            cuenta.setStatus(request.status());

            Account updatedAccount = accountRepository.save(cuenta);

            return new AccountResponseDTO(
                    updatedAccount.getId(),
                    updatedAccount.getAccountNumber(),
                    updatedAccount.getAccountType(),
                    updatedAccount.getInitialBalance(),
                    updatedAccount.getCurrentBalance(),
                    updatedAccount.getStatus(),
                    updatedAccount.getClientId()
            );
        });
    }

}
