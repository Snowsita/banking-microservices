package com.etorres.banking.clients.controller;

import com.etorres.banking.clients.dto.ClientDTO;
import com.etorres.banking.clients.dto.CreateClientRequest;
import com.etorres.banking.clients.service.ClientService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/clients")
public class ClientController {

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
        return clientService.createClient(createClientRequest);
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
        return clientService.getAllClients();
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
        return clientService.getClientById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
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
        return clientService.getClientById(clientId)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
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
        return clientService.updateCliente(id, clientDTO)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
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
        if (clientService.deleteClient(id)) {
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
