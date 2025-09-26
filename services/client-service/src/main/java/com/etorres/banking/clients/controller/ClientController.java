package com.etorres.banking.clients.controller;

import com.etorres.banking.clients.dto.ClientDTO;
import com.etorres.banking.clients.dto.CreateClientRequest;
import com.etorres.banking.clients.service.ClientService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/clients")
public class ClientController {

    private static final Logger log = LoggerFactory.getLogger(ClientController.class);
    private final ClientService clientService;

    /**
     * Crea un nuevo cliente.
     * HTTP METHOD: POST
     * URL: /api/v1/clientes
     * Response Status: 201 Created
     * @param createClientRequest La data del cliente a crear.
     * @return La data creada del cliente.
     */
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ClientDTO createClient(@RequestBody @Valid CreateClientRequest createClientRequest) {
        log.info("INFO: Creando nuevo cliente: {}", createClientRequest.clientId());
        ClientDTO createdClient = clientService.createClient(createClientRequest);
        log.info("INFO: Cliente creado exitosamente: {}", createdClient.clientId());
        return createdClient;
    }

    /**
     * Obtiene la lista de todos los clientes.
     * HTTP METHOD: GET
     * URL: /api/v1/clientes
     * Response Status: 200 OK
     * @return Lista de clientes.
     */
    @GetMapping
    public List<ClientDTO> getAllClients() {
        log.info("INFO: Obteniendo todos los clientes");
        List<ClientDTO> clients = clientService.getAllClients();
        log.info("INFO: Se encontraron {} clientes", clients.size());
        return clients;
    }

    /**
     * Obtiene la data de un cliente por su ID.
     * HTTP METHOD: GET
     * URL: /api/v1/clientes/{id}
     * Response Status: 200 OK si se encuentra, 404 Not Found si no se encuentra.
     * @param id El ID del cliente a buscar.
     * @return La data del cliente si se encuentra, 404 Not Found en caso contrario.
     */
    @GetMapping("/{id}")
    public ResponseEntity<ClientDTO> getClientById(@PathVariable Long id) {
        log.info("INFO: Buscando cliente por ID: {}", id);
        return clientService.getClientById(id)
                .map(client -> {
                    log.info("INFO: Cliente encontrado: {}", client.clientId());
                    return ResponseEntity.ok(client);
                })
                .orElseGet(() -> {
                    log.warn("WARN: Cliente no encontrado con ID: {}", id);
                    return ResponseEntity.notFound().build();
                });
    }

    /**
     * Obtiene la data de un cliente por su clientId.
     * HTTP METHOD: GET
     * URL: /api/v1/clientes/clientId/{clientId}
     * Response Status: 200 OK si se encuentra, 404 Not Found si no se encuentra.
     * @param clientId El clientId del cliente a buscar.
     * @return La data del cliente si se encuentra, 404 Not Found en caso contrario.
     */
    @GetMapping("/clientId/{clientId}")
    public ResponseEntity<ClientDTO> getClientByClientId(@PathVariable String clientId) {
        log.info("INFO: Buscando cliente por clientId: {}", clientId);
        return clientService.getClientById(clientId)
                .map(client -> {
                    log.info("INFO: Cliente encontrado: {}", client.clientId());
                    return ResponseEntity.ok(client);
                })
                .orElseGet(() -> {
                    log.warn("WARN: Cliente no encontrado con clientId: {}", clientId);
                    return ResponseEntity.notFound().build();
                });
    }

    /**
     * Actualiza la data de un cliente existente.
     * HTTP METHOD: PUT
     * URL: /api/v1/clientes/{id}
     * Response Status: 200 OK si se actualiza, 404 Not Found si no se encuentra.
     * @param id El ID del cliente a actualizar.
     * @param clientDTO La nueva data del cliente.
     * @return La data actualizada del cliente si se encuentra, 404 Not Found en caso contrario.
     */
    @PutMapping("/{id}")
    public ResponseEntity<ClientDTO> updateClient(@PathVariable Long id, @RequestBody ClientDTO clientDTO) {
        log.info("INFO: Actualizando cliente con ID: {}", id);
        return clientService.updateCliente(id, clientDTO)
                .map(updatedClient -> {
                    log.info("INFO: Cliente actualizado exitosamente: {}", updatedClient.clientId());
                    return ResponseEntity.ok(updatedClient);
                })
                .orElseGet(() -> {
                    log.warn("WARN: Cliente no encontrado para actualizar con ID: {}", id);
                    return ResponseEntity.notFound().build();
                });
    }

    /**
     * Elimina un cliente por su ID.
     * HTTP METHOD: DELETE
     * URL: /api/v1/clientes/{id}
     * Response Status: 204 No Content si se elimina, 404 Not Found si no se encuentra.
     * @param id El ID del cliente a eliminar.
     * @return 204 No Content si se elimina, 404 Not Found en caso contrario.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteClient(@PathVariable Long id) {
        log.info("INFO: Eliminando cliente con ID: {}", id);
        if (clientService.deleteClient(id)) {
            log.info("INFO: Cliente eliminado exitosamente con ID: {}", id);
            return ResponseEntity.noContent().build();
        } else {
            log.warn("WARN: Cliente no encontrado para eliminar con ID: {}", id);
            return ResponseEntity.notFound().build();
        }
    }
}
