package fr.benseddik.gestioncmd.repository;

import fr.benseddik.gestioncmd.domain.Client;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface ClientRepository extends JpaRepository<Client, UUID> {
}
