package com.etorres.banking.accounts.service;

import com.etorres.banking.accounts.dto.CuentaRequestDTO;
import com.etorres.banking.accounts.dto.CuentaResponseDTO;
import com.etorres.banking.accounts.dto.CuentaUpdateRequestDTO;

import java.util.Optional;

public interface CuentaService {

    /**
     * Crea una nueva cuenta bancaria basada en los datos proporcionados en el DTO de solicitud.
     *
     * @param request El DTO que contiene los detalles necesarios para crear la cuenta.
     * @return Un DTO de respuesta que contiene los detalles de la cuenta creada.
     */
    CuentaResponseDTO create(CuentaRequestDTO request);

    /**
     * Busca una cuenta bancaria por su número de cuenta.
     *
     * @param accountNumber El número de cuenta a buscar.
     * @return Un Optional que contiene el DTO de respuesta si se encuentra la cuenta, o vacío si no se encuentra.
     */
    Optional<CuentaResponseDTO> findByAccountNumber(String accountNumber);

    /**
     * Actualiza los detalles de una cuenta bancaria existente.
     *
     * @param accountNumber El número de cuenta de la cuenta a actualizar.
     * @param request       El DTO que contiene los nuevos detalles para la cuenta.
     * @return Un Optional que contiene el DTO de respuesta con los detalles actualizados si la cuenta existe, o vacío si no se encuentra.
     */
    Optional<CuentaResponseDTO> update(String accountNumber, CuentaUpdateRequestDTO request);
}
