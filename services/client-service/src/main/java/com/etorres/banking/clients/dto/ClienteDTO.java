package com.etorres.banking.clients.dto;

public record ClienteDTO(
        Long id,
        String clientId,
        String name,
        String gender,
        int age,
        String identification,
        String address,
        String phone,
        Boolean status
) {
}
