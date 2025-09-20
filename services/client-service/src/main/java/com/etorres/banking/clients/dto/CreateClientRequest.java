package com.etorres.banking.clients.dto;

public record CreateClientRequest(
        String clientId,
        String password,
        String name,
        String gender,
        int age,
        String identification,
        String address,
        String phone,
        Boolean status
) {
}
