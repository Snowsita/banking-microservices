package com.etorres.banking.clients.service;

import com.etorres.banking.clients.dto.ClientDTO;
import com.etorres.banking.clients.dto.CreateClientRequest;
import com.etorres.banking.clients.model.Client;
import com.etorres.banking.clients.repository.ClientRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ClientServiceImpl implements ClientService {

    private final ClientRepository clientRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    @Transactional
    public ClientDTO createClient(CreateClientRequest clientDTO) {
        Client client = new Client();
        client.setClientId(clientDTO.clientId());
        client.setPassword(passwordEncoder.encode(clientDTO.password()));
        client.setName(clientDTO.name());
        client.setGender(clientDTO.gender());
        client.setAge(clientDTO.age());
        client.setIdentification(clientDTO.identification());
        client.setAddress(clientDTO.address());
        client.setPhone(clientDTO.phone());
        client.setStatus(clientDTO.status());

        Client savedClient = clientRepository.save(client);
        return convertToDTO(savedClient);
    }

    @Override
    @Transactional(readOnly = true)
    public List<ClientDTO> getAllClients() {
        List<Client> clients = clientRepository.findAll();
        return clients.stream().map(this::convertToDTO).toList();
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<ClientDTO> getClientById(Long id) {
        return clientRepository.findById(id).map(this::convertToDTO);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<ClientDTO> getClientById(String clientId) {
        return clientRepository.findByClientId(clientId).map(this::convertToDTO);
    }

    @Override
    @Transactional
    public Optional<ClientDTO> updateCliente(Long id, ClientDTO clientDTO) {
        return clientRepository.findById(id).map(existingClient -> {
            existingClient.setName(clientDTO.name());
            existingClient.setGender(clientDTO.gender());
            existingClient.setAge(clientDTO.age());
            existingClient.setIdentification(clientDTO.identification());
            existingClient.setAddress(clientDTO.address());
            existingClient.setPhone(clientDTO.phone());
            existingClient.setStatus(clientDTO.status());
            Client updatedClient = clientRepository.save(existingClient);

            return convertToDTO(updatedClient);
        });
    }

    @Override
    @Transactional
    public boolean deleteClient(Long id) {
        if (clientRepository.existsById(id)) {
            clientRepository.deleteById(id);
            return true;
        }
        return false;
    }

    private ClientDTO convertToDTO(Client client) {
        return new ClientDTO(
                client.getId(),
                client.getClientId(),
                client.getName(),
                client.getGender(),
                client.getAge(),
                client.getIdentification(),
                client.getAddress(),
                client.getPhone(),
                client.getStatus()
        );
    }
}
