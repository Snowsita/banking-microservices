package com.etorres.banking.accounts.service;

import com.etorres.banking.accounts.dto.CuentaRequestDTO;
import com.etorres.banking.accounts.dto.CuentaResponseDTO;

public interface CuentaService {

    /**
     * Crea una nueva cuenta bancaria basada en los datos proporcionados en el DTO de solicitud.
     *
     * @param request El DTO que contiene los detalles necesarios para crear la cuenta.
     * @return Un DTO de respuesta que contiene los detalles de la cuenta creada.
     */
    CuentaResponseDTO create(CuentaRequestDTO request);
}
