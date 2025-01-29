package fr.benseddik.gestioncmd.service;

import fr.benseddik.gestioncmd.domain.Client;
import fr.benseddik.gestioncmd.domain.Dish;
import fr.benseddik.gestioncmd.domain.Order;
import fr.benseddik.gestioncmd.domain.OrderItem;
import fr.benseddik.gestioncmd.dto.OrderItemDTO;
import fr.benseddik.gestioncmd.dto.OrderRequestDTO;
import fr.benseddik.gestioncmd.exception.ResourceNotFoundException;
import fr.benseddik.gestioncmd.repository.ClientRepository;
import fr.benseddik.gestioncmd.repository.DishRepository;
import fr.benseddik.gestioncmd.repository.OrderRepository;
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


class OrderServiceTest {

    @Mock
    private OrderRepository orderRepository;

    @Mock
    private ClientRepository clientRepository;

    @Mock
    private DishRepository dishRepository;

    @InjectMocks
    private OrderService orderService;

    private UUID clientId;
    private UUID dishId;
    private Client client;
    private Dish dish;
    private Order savedOrder;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        clientId = UUID.randomUUID();
        dishId = UUID.randomUUID();
        client = new Client(clientId, "Alice Dupont", "alice@example.com", "0601020304", null);
        dish = new Dish(dishId, "Pizza Margherita", 12.50, true);
        savedOrder = new Order(); // Instanciation de l'objet Order
        savedOrder.setId(UUID.randomUUID()); // Donne un ID unique à l'objet
        savedOrder.setClient(client); // Associe un client
        savedOrder.setItems(List.of(new OrderItem())); // Ajoute des items à la commande (si nécessaire)
        savedOrder.setTotalPrice(10.0);
    }

    @Test
    void testPlaceOrder_Success() {
        OrderRequestDTO orderRequest = new OrderRequestDTO(clientId, List.of(new OrderItemDTO(dishId, 2)));

        when(clientRepository.findById(clientId)).thenReturn(Optional.of(client));
        when(dishRepository.findById(dishId)).thenReturn(Optional.of(dish));

        // Mock de la sauvegarde de l'objet Order
        when(orderRepository.save(any(Order.class))).thenReturn(savedOrder);
        // Exécution du test, on vérifie qu'aucune exception n'est lancée
        assertDoesNotThrow(() -> orderService.placeOrder(orderRequest));
    }

    @Test
    void testPlaceOrder_ClientNotFound() {
        OrderRequestDTO orderRequest = new OrderRequestDTO(clientId, List.of(new OrderItemDTO(dishId, 2)));

        when(clientRepository.findById(clientId)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> orderService.placeOrder(orderRequest));
    }

    @Test
    void testPlaceOrder_DishNotFound() {
        OrderRequestDTO orderRequest = new OrderRequestDTO(clientId, List.of(new OrderItemDTO(dishId, 2)));

        when(clientRepository.findById(clientId)).thenReturn(Optional.of(client));
        when(dishRepository.findById(dishId)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> orderService.placeOrder(orderRequest));
    }
}
