package com.etorres.banking.accounts.mapper;

import com.etorres.banking.accounts.dto.AccountStatementDTO;
import com.etorres.banking.accounts.dto.MovementResponseDTO;
import com.etorres.banking.accounts.model.Account;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface AccountStatementMapper {
    AccountStatementDTO.AccountDetail toAccountDetail(Account account, List<MovementResponseDTO> movements);
}
