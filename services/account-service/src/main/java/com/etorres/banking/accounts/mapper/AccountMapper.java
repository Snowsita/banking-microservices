package com.etorres.banking.accounts.mapper;

import com.etorres.banking.accounts.dto.AccountRequestDTO;
import com.etorres.banking.accounts.dto.AccountResponseDTO;
import com.etorres.banking.accounts.dto.AccountUpdateRequestDTO;
import com.etorres.banking.accounts.model.Account;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface AccountMapper {

    AccountResponseDTO toDto(Account account);

    @Mapping(target = "currentBalance", source = "initialBalance")
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "movements", ignore = true)
    Account toEntity(AccountRequestDTO accountRequestDTO);

    void updateEntityFromDto(AccountUpdateRequestDTO accountUpdateRequestDTO, @MappingTarget Account account);
}
