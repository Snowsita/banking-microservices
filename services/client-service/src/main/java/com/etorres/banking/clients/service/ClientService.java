package com.etorres.banking.clients.service;

import com.etorres.banking.clients.dto.ClientDTO;
import com.etorres.banking.clients.dto.CreateClientRequest;

import java.util.List;
import java.util.Optional;

public interface ClientService {

    /**
     * Crea un nuevo cliente en el sistema.
     * @param clientDTO La data del cliente a crear.
     * @return La data creada del cliente.
     */
    ClientDTO createClient(CreateClientRequest clientDTO);

    /**
     * Obtiene la lista de todos los clientes en el sistema.
     * @return Lista de clientes.
     */
    List<ClientDTO> getAllClients();

    /**
     * Obtiene la data de un cliente por su ID.
     * @param id El ID del cliente a buscar.
     * @return La data del cliente si se encuentra, opcional en caso contrario.
     */
    Optional<ClientDTO> getClientById(Long id);

    /**
     * Obtiene la data de un cliente por su clientId.
     * @param clientId El clientId del cliente a buscar.
     * @return La data del cliente si se encuentra, opcional en caso contrario.
     */
    Optional<ClientDTO> getClientById(String clientId);

    /**
     * Actualiza la data de un cliente existente.
     * @param id El ID del cliente a actualizar.
     * @param clientDTO La nueva data del cliente.
     * @return La data actualizada del cliente si se encuentra, opcional en caso contrario.
     */
    Optional<ClientDTO> updateCliente(Long id, ClientDTO clientDTO);

    /**
     * Elimina un cliente del sistema por su ID.
     * @param id El ID del cliente a eliminar.
     * @return Verdadero si el cliente fue eliminado, falso si no se encontró.
     */
    boolean deleteClient(Long id);
}
