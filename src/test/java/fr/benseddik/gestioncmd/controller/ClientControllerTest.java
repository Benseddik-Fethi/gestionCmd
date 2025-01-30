package fr.benseddik.gestioncmd.controller;


import fr.benseddik.gestioncmd.dto.ClientDTO;
import fr.benseddik.gestioncmd.exception.ResourceNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.UUID;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

class ClientControllerTest {

    private MockMvc mockMvc;

    @Mock
    private fr.benseddik.gestioncmd.service.ClientService clientService;

    @InjectMocks
    private ClientController clientController;

    private UUID clientId;
    private ClientDTO clientDTO;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(clientController).build();

        clientId = UUID.randomUUID();
        clientDTO = new ClientDTO(clientId, "Alice Dupont", "alice@example.com", "0601020304");
    }

    @Test
    void testGetAllClients() throws Exception {
        when(clientService.getAllClients()).thenReturn(List.of(clientDTO));

        mockMvc.perform(get("/clients"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(clientId.toString()))
                .andExpect(jsonPath("$[0].name").value("Alice Dupont"))
                .andExpect(jsonPath("$[0].email").value("alice@example.com"));
    }

    @Test
    void testGetClientById_Success() throws Exception {
        when(clientService.getClientById(clientId)).thenReturn(clientDTO);

        mockMvc.perform(get("/clients/{id}", clientId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(clientId.toString()))
                .andExpect(jsonPath("$.name").value("Alice Dupont"));
    }
}
