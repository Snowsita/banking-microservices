package com.etorres.banking.accounts.mapper;

import com.etorres.banking.accounts.dto.MovementResponseDTO;
import com.etorres.banking.accounts.model.Movement;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface MovementMapper {
    MovementResponseDTO toDto(Movement movement);
}
