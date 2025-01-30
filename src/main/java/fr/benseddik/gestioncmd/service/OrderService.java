package fr.benseddik.gestioncmd.service;

import fr.benseddik.gestioncmd.domain.Client;
import fr.benseddik.gestioncmd.domain.Dish;
import fr.benseddik.gestioncmd.domain.Order;
import fr.benseddik.gestioncmd.domain.OrderItem;
import fr.benseddik.gestioncmd.dto.OrderItemDTO;
import fr.benseddik.gestioncmd.dto.OrderRequestDTO;
import fr.benseddik.gestioncmd.dto.OrderResponseDTO;
import fr.benseddik.gestioncmd.exception.BusinessLogicException;
import fr.benseddik.gestioncmd.exception.ResourceNotFoundException;
import fr.benseddik.gestioncmd.repository.ClientRepository;
import fr.benseddik.gestioncmd.repository.DishRepository;
import fr.benseddik.gestioncmd.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class OrderService {

    private final OrderRepository orderRepository;
    private final ClientRepository clientRepository;
    private final DishRepository dishRepository;

    public OrderResponseDTO placeOrder(OrderRequestDTO orderRequestDTO) {
        // Vérifier si le client existe
        Client client = clientRepository.findById(orderRequestDTO.clientId())
                .orElseThrow(() -> new ResourceNotFoundException("Client non trouvé"));

        // Vérifier si la commande contient au moins un plat
        if (orderRequestDTO.items().isEmpty()) {
            throw new BusinessLogicException("Une commande doit contenir au moins un plat");
        }

        // Créer la commande
        Order order = new Order(null, client, LocalDateTime.now(), 0, null);
        double totalPrice = 0;
        List<OrderItem> items = new ArrayList<>();

        // Parcourir les articles de la commande
        for (OrderItemDTO itemDTO : orderRequestDTO.items()) {
            Dish dish = dishRepository.findById(itemDTO.dishId())
                    .orElseThrow(() -> new ResourceNotFoundException("Plat non trouvé"));

            // Vérifier la disponibilité du plat
            if (!dish.isAvailable()) {
                throw new BusinessLogicException("Le plat " + dish.getName() + " n'est plus disponible.");
            }

            // Calculer le prix total et ajouter l'article à la commande
            totalPrice += dish.getPrice() * itemDTO.quantity();
            items.add(new OrderItem(null, order, dish, itemDTO.quantity()));
        }

        // Sauvegarder la commande
        order.setItems(items);
        order.setTotalPrice(totalPrice);
        Order savedOrder = orderRepository.save(order);

        // Retourner une réponse DTO
        return new OrderResponseDTO(
                savedOrder.getId(),
                savedOrder.getClient().getId(),
                savedOrder.getOrderDate(),
                savedOrder.getTotalPrice(),
                orderRequestDTO.items()
        );
    }

}

