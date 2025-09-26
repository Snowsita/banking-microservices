package com.etorres.banking.clients.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record CreateClientRequest(
        @NotBlank(message = "El ID del cliente no puede estar vacío")
        String clientId,
        @NotBlank(message = "La contraseña no puede estar vacía")
        @Size(min = 6, message = "La contraseña debe tener al menos 6 caracteres")
        String password,
        @NotBlank(message = "El nombre no puede estar vacío")
        String name,
        String gender,
        int age,
        @NotBlank(message = "La identificación no puede estar vacía")
        String identification,
        @NotBlank(message = "La dirección no puede estar vacía")
        String address,
        @NotBlank(message = "El teléfono no puede estar vacío")
        String phone,
        Boolean status
) {
}
