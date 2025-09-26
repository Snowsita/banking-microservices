package com.etorres.banking.accounts.mapper;

import com.etorres.banking.accounts.dto.MovementRequestDTO;
import com.etorres.banking.accounts.dto.MovementResponseDTO;
import com.etorres.banking.accounts.model.Account;
import com.etorres.banking.accounts.model.Movement;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Mapper(componentModel = "spring")
public interface MovementMapper {
    MovementResponseDTO toDto(Movement movement);

    @Mapping(target = "id", ignore = true)
    @Mapping(source = "request.value", target = "value")
    @Mapping(source = "newBalance", target = "balance")
    Movement toEntity(MovementRequestDTO request, Account account, BigDecimal newBalance, LocalDateTime date, String movementType);
}
