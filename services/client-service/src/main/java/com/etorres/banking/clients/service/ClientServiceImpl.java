package com.etorres.banking.clients.service;

import com.etorres.banking.clients.dto.ClientDTO;
import com.etorres.banking.clients.dto.CreateClientRequest;
import com.etorres.banking.clients.mapper.ClientMapper;
import com.etorres.banking.clients.model.Client;
import com.etorres.banking.clients.repository.ClientRepository;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ClientServiceImpl implements ClientService {

    private static final Logger log = LoggerFactory.getLogger(ClientServiceImpl.class);
    private final ClientRepository clientRepository;
    private final PasswordEncoder passwordEncoder;
    private final ClientMapper clientMapper;

    @Override
    @Transactional
    public ClientDTO createClient(CreateClientRequest createClientRequest) {
        log.info("INFO: Creando un nuevo cliente con clientId: {}", createClientRequest.clientId());
        Client client = clientMapper.toEntity(createClientRequest);

        client.setPassword(passwordEncoder.encode(createClientRequest.password()));

        Client savedClient = clientRepository.save(client);
        log.info("INFO: Cliente guardado con ID: {}", savedClient.getId());
        return clientMapper.toDto(savedClient);
    }

    @Override
    @Transactional(readOnly = true)
    public List<ClientDTO> getAllClients() {
        log.info("INFO: Obteniendo todos los clientes");
        List<Client> clients = clientRepository.findAll();
        log.info("INFO: Encontrados {} clientes", clients.size());
        return clients.stream().map(clientMapper::toDto).toList();
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<ClientDTO> getClientById(Long id) {
        log.info("INFO: Buscando cliente por ID: {}", id);
        Optional<ClientDTO> clientDTO = clientRepository.findById(id).map(clientMapper::toDto);
        if (clientDTO.isPresent()) {
            log.info("INFO: Cliente encontrado con ID: {}", id);
        } else {
            log.warn("WARN: Cliente no encontrado con ID: {}", id);
        }
        return clientDTO;
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<ClientDTO> getClientById(String clientId) {
        log.info("INFO: Buscando cliente por clientId: {}", clientId);
        Optional<ClientDTO> clientDTO = clientRepository.findByClientId(clientId).map(clientMapper::toDto);
        if (clientDTO.isPresent()) {
            log.info("INFO: Cliente encontrado con clientId: {}", clientId);
        } else {
            log.warn("WARN: Cliente no encontrado con clientId: {}", clientId);
        }
        return clientDTO;
    }

    @Override
    @Transactional
    public Optional<ClientDTO> updateClient(Long id, ClientDTO clientDTO) {
        log.info("INFO: Actualizando cliente con ID: {}", id);
        return clientRepository.findById(id).map(existingClient -> {
            log.info("INFO: Cliente encontrado para actualizar. ID: {}", id);
            clientMapper.updateEntityFromDto(clientDTO, existingClient);

            Client updatedClient = clientRepository.save(existingClient);
            log.info("INFO: Cliente actualizado con ID: {}", updatedClient.getId());

            return clientMapper.toDto(updatedClient);
        });
    }

    @Override
    @Transactional
    public boolean deleteClient(Long id) {
        log.info("INFO: Intentando eliminar cliente con ID: {}", id);
        if (clientRepository.existsById(id)) {
            clientRepository.deleteById(id);
            log.info("INFO: Cliente eliminado con ID: {}", id);
            return true;
        }
        log.warn("WARN: No se pudo eliminar. Cliente no encontrado con ID: {}", id);
        return false;
    }
}
