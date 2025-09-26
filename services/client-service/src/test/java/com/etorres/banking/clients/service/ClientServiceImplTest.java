package com.etorres.banking.clients.service;

import com.etorres.banking.clients.dto.ClientDTO;
import com.etorres.banking.clients.dto.CreateClientRequest;
import com.etorres.banking.clients.mapper.ClientMapper;
import com.etorres.banking.clients.model.Client;
import com.etorres.banking.clients.repository.ClientRepository;
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
public class ClientServiceImplTest {

    @Mock
    private ClientRepository clientRepository;

    @Mock
    private ClientMapper clientMapper;

    @Mock
    private PasswordEncoder passwordEncoder;

    @InjectMocks
    private ClientServiceImpl clientService;

    /**
     * Test para verificar la creación de un cliente con datos válidos.
     * Verifica que el cliente se guarda correctamente y que la contraseña se encripta.
     */
    @Test
    void whenCreateClient_withValidData_thenClientIsCreated() {
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

        var clientEntity = new Client();
        var savedClient = new Client();
        savedClient.setId(1L);
        var expectedDto = new ClientDTO(1L, "C-12345", "Enmanuel Torres", null, 0, null, null, null, true);

        when(clientMapper.toEntity(createRequest)).thenReturn(clientEntity);
        when(passwordEncoder.encode(createRequest.password())).thenReturn("hashedPassword");
        when(clientRepository.save(clientEntity)).thenReturn(savedClient);
        when(clientMapper.toDto(savedClient)).thenReturn(expectedDto);

        ClientDTO resultDTO = clientService.createClient(createRequest);

        assertNotNull(resultDTO);
        assertEquals(1L, resultDTO.id());
        assertEquals("Enmanuel Torres", resultDTO.name());
        assertEquals("C-12345", resultDTO.clientId());

        verify(passwordEncoder, times(1)).encode("rawPassword123");
        verify(clientRepository, times(1)).save(any(Client.class));
        verify(clientMapper, times(1)).toEntity(createRequest);
        verify(clientMapper, times(1)).toDto(savedClient);
    }
}
