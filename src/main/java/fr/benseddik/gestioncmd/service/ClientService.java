package fr.benseddik.gestioncmd.service;



import fr.benseddik.gestioncmd.domain.Client;
import fr.benseddik.gestioncmd.dto.ClientDTO;
import fr.benseddik.gestioncmd.exception.ResourceNotFoundException;
import fr.benseddik.gestioncmd.repository.ClientRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ClientService {

    private final ClientRepository clientRepository;


    public ClientDTO createClient(ClientDTO clientDTO) {
        Client client = new Client(null, clientDTO.name(), clientDTO.email(), clientDTO.phone(), null);
        Client savedClient = clientRepository.save(client);
        return new ClientDTO(savedClient.getId(), savedClient.getName(), savedClient.getEmail(), savedClient.getPhone());
    }

    public List<ClientDTO> getAllClients() {
        return clientRepository.findAll()
                .stream()
                .map(client -> new ClientDTO(client.getId(), client.getName(), client.getEmail(), client.getPhone()))
                .collect(Collectors.toList());
    }

    public ClientDTO getClientById(UUID id) {
        Client client = clientRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Client avec ID " + id + " non trouvé"));
        return new ClientDTO(client.getId(), client.getName(), client.getEmail(), client.getPhone());
    }
}
