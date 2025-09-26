package com.etorres.banking.accounts.service;

import com.etorres.banking.accounts.dto.MovementRequestDTO;
import com.etorres.banking.accounts.dto.MovementResponseDTO;

public interface MovementService {

    /**
     * Crea un nuevo movimiento bancario basado en los datos proporcionados en el DTO de solicitud.
     *
     * @param request El DTO que contiene los detalles necesarios para crear el movimiento.
     * @return Un DTO de respuesta que contiene los detalles del movimiento creado.
     */
    MovementResponseDTO create(MovementRequestDTO request);
}
