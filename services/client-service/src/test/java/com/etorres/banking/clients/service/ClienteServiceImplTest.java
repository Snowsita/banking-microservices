package com.etorres.banking.clients.service;

import com.etorres.banking.clients.dto.ClienteDTO;
import com.etorres.banking.clients.dto.CreateClientRequest;
import com.etorres.banking.clients.model.Cliente;
import com.etorres.banking.clients.repository.ClienteRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class ClienteServiceImplTest {

    @Mock
    private ClienteRepository clienteRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @InjectMocks
    private ClienteServiceImpl clienteService;

    /**
     * Test para verificar la creación de un cliente con datos válidos.
     * Verifica que el cliente se guarda correctamente y que la contraseña se encripta.
     */
    @Test
    void whenCreateCliente_withValidData_thenClienteIsCreated() {
        var createRequest = new CreateClientRequest(
                "C-12345",
                "rawPassword123",
                "Enmanuel Torres",
                "Male",
                30,
                "001-150795-0012A",
                "123 Main St",
                "8888-7777",
                true
        );

        var savedCliente = new Cliente();
        savedCliente.setId(1L);
        savedCliente.setClientId(createRequest.clientId());
        savedCliente.setName(createRequest.name());
        savedCliente.setPassword("hashedPassword");

        when(passwordEncoder.encode(createRequest.password())).thenReturn("hashedPassword");
        when(clienteRepository.save(any(Cliente.class))).thenReturn(savedCliente);

        ClienteDTO resultDTO = clienteService.createCliente(createRequest);

        assertNotNull(resultDTO);
        assertEquals(1L, resultDTO.id());
        assertEquals("Enmanuel Torres", resultDTO.name());
        assertEquals("C-12345", resultDTO.clientId());

        verify(passwordEncoder, times(1)).encode("rawPassword123");
        verify(clienteRepository, times(1)).save(any(Cliente.class));
    }
}
