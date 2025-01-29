package fr.benseddik.gestioncmd.repository;

import fr.benseddik.gestioncmd.domain.OrderItem;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface OrderItemRepository extends JpaRepository<OrderItem, UUID> {
}
