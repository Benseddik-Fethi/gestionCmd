package fr.benseddik.gestioncmd.service;

import fr.benseddik.gestioncmd.domain.Client;
import fr.benseddik.gestioncmd.dto.ClientDTO;
import fr.benseddik.gestioncmd.exception.ResourceNotFoundException;
import fr.benseddik.gestioncmd.repository.ClientRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class ClientServiceTest {

    @Mock
    private ClientRepository clientRepository;

    @InjectMocks
    private ClientService clientService;

    private Client client;
    private UUID clientId;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        clientId = UUID.randomUUID();
        client = new Client(clientId, "Alice Dupont", "alice@example.com", "0601020304", null);
    }

    @Test
    void testGetClientById_Success() {
        when(clientRepository.findById(clientId)).thenReturn(Optional.of(client));

        ClientDTO result = clientService.getClientById(clientId);

        assertNotNull(result);
        assertEquals(client.getId(), result.id());
        assertEquals(client.getName(), result.name());
        assertEquals(client.getEmail(), result.email());
    }

    @Test
    void testGetClientById_NotFound() {
        when(clientRepository.findById(clientId)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> clientService.getClientById(clientId));
    }
}
