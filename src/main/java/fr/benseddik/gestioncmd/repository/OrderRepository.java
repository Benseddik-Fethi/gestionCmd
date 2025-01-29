package fr.benseddik.gestioncmd.repository;
import fr.benseddik.gestioncmd.domain.Order;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface OrderRepository extends JpaRepository<Order, UUID> {
    List<Order> findByClientId(UUID clientId); // Récupérer les commandes d'un client
}
