package com.etorres.banking.clients.mapper;

import com.etorres.banking.clients.dto.ClientDTO;
import com.etorres.banking.clients.dto.CreateClientRequest;
import com.etorres.banking.clients.model.Client;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface ClientMapper {

    ClientDTO toDto(Client client);

    @Mapping(target = "id", ignore = true)
    Client toEntity(CreateClientRequest createClientRequest);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "clientId", ignore = true)
    void updateEntityFromDto(ClientDTO clientDTO, @MappingTarget Client client);
}
