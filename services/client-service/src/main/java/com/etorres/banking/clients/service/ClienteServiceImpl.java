package com.etorres.banking.clients.service;

import com.etorres.banking.clients.dto.ClienteDTO;
import com.etorres.banking.clients.model.Cliente;
import com.etorres.banking.clients.repository.ClienteRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ClienteServiceImpl implements ClienteService {

    private final ClienteRepository clienteRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    @Transactional
    public ClienteDTO createCliente(ClienteDTO clienteDTO) {
        Cliente cliente = convertToEntity(clienteDTO);
        cliente.setPassword(passwordEncoder.encode("password"));
        Cliente savedCliente = clienteRepository.save(cliente);
        return convertToDTO(savedCliente);
    }

    @Override
    @Transactional(readOnly = true)
    public List<ClienteDTO> getAllClientes() {
        List<Cliente> clientes = clienteRepository.findAll();
        return clientes.stream().map(this::convertToDTO).toList();
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<ClienteDTO> getClienteById(Long id) {
        return clienteRepository.findById(id).map(this::convertToDTO);
    }

    @Override
    @Transactional
    public Optional<ClienteDTO> updateCliente(Long id, ClienteDTO clienteDTO) {
        return clienteRepository.findById(id).map(existingCliente -> {
            existingCliente.setName(clienteDTO.name());
            existingCliente.setGender(clienteDTO.gender());
            existingCliente.setAge(clienteDTO.age());
            existingCliente.setIdentification(clienteDTO.identification());
            existingCliente.setAddress(clienteDTO.address());
            existingCliente.setPhone(clienteDTO.phone());
            existingCliente.setStatus(clienteDTO.status());
            Cliente updatedCliente = clienteRepository.save(existingCliente);

            return convertToDTO(updatedCliente);
        });
    }

    @Override
    @Transactional
    public boolean deleteCliente(Long id) {
        if (clienteRepository.existsById(id)) {
            clienteRepository.deleteById(id);
            return true;
        }
        return false;
    }

    private ClienteDTO convertToDTO(Cliente cliente) {
        return new ClienteDTO(
                cliente.getId(),
                cliente.getClientId(),
                cliente.getName(),
                cliente.getGender(),
                cliente.getAge(),
                cliente.getIdentification(),
                cliente.getAddress(),
                cliente.getPhone(),
                cliente.getStatus()
        );
    }

    private Cliente convertToEntity(ClienteDTO clienteDTO) {
        Cliente cliente = new Cliente();
        cliente.setClientId(clienteDTO.clientId());
        cliente.setPassword("temporary_password"); // TODO: Replace with actual password hashing
        cliente.setName(clienteDTO.name());
        cliente.setGender(clienteDTO.gender());
        cliente.setAge(clienteDTO.age());
        cliente.setIdentification(clienteDTO.identification());
        cliente.setAddress(clienteDTO.address());
        cliente.setPhone(clienteDTO.phone());
        cliente.setStatus(clienteDTO.status());

        return cliente;
    }
}
