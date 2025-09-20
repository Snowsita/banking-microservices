package com.etorres.banking.clients.service;

import com.etorres.banking.clients.dto.ClienteDTO;

import java.util.List;
import java.util.Optional;

public interface ClienteService {

    /**
     * Crea un nuevo cliente en el sistema.
     * @param clienteDTO La data del cliente a crear.
     * @return La data creada del cliente.
     */
    ClienteDTO createCliente(ClienteDTO clienteDTO);

    /**
     * Obtiene la lista de todos los clientes en el sistema.
     * @return Lista de clientes.
     */
    List<ClienteDTO> getAllClientes();

    /**
     * Obtiene la data de un cliente por su ID.
     * @param id El ID del cliente a buscar.
     * @return La data del cliente si se encuentra, opcional en caso contrario.
     */
    Optional<ClienteDTO> getClienteById(Long id);

    /**
     * Actualiza la data de un cliente existente.
     * @param id El ID del cliente a actualizar.
     * @param clienteDTO La nueva data del cliente.
     * @return La data actualizada del cliente si se encuentra, opcional en caso contrario.
     */
    Optional<ClienteDTO> updateCliente(Long id, ClienteDTO clienteDTO);

    /**
     * Elimina un cliente del sistema por su ID.
     * @param id El ID del cliente a eliminar.
     * @return Verdadero si el cliente fue eliminado, falso si no se encontró.
     */
    boolean deleteCliente(Long id);
}
