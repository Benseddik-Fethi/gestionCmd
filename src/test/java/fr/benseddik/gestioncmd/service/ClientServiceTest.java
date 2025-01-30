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

import java.util.List;
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
    void testCreateClient() {
        when(clientRepository.save(any(Client.class))).thenReturn(client);

        ClientDTO clientDTO = new ClientDTO(null, "Alice Dupont", "alice@example.com", "0601020304");
        ClientDTO savedClient = clientService.createClient(clientDTO);

        assertNotNull(savedClient);
        assertEquals("Alice Dupont", savedClient.name());
        verify(clientRepository, times(1)).save(any(Client.class));
    }

    @Test
    void testGetAllClients() {
        when(clientRepository.findAll()).thenReturn(List.of(client));

        List<ClientDTO> clients = clientService.getAllClients();

        assertFalse(clients.isEmpty());
        assertEquals(1, clients.size());
        assertEquals("Alice Dupont", clients.get(0).name());
    }

    @Test
    void testGetClientById_Success() {
        when(clientRepository.findById(clientId)).thenReturn(Optional.of(client));

        ClientDTO result = clientService.getClientById(clientId);

        assertNotNull(result);
        assertEquals(client.getId(), result.id());
    }

    @Test
    void testGetClientById_NotFound() {
        when(clientRepository.findById(clientId)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> clientService.getClientById(clientId));
    }
}
