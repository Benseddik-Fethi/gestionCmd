package fr.benseddik.gestioncmd;

import fr.benseddik.gestioncmd.domain.Client;
import fr.benseddik.gestioncmd.domain.Dish;
import fr.benseddik.gestioncmd.domain.Order;
import fr.benseddik.gestioncmd.domain.OrderItem;
import fr.benseddik.gestioncmd.repository.ClientRepository;
import fr.benseddik.gestioncmd.repository.DishRepository;
import fr.benseddik.gestioncmd.repository.OrderItemRepository;
import fr.benseddik.gestioncmd.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.List;

@SpringBootApplication
@RequiredArgsConstructor
public class GestionCmdApplication implements CommandLineRunner {
    private final ClientRepository clientRepository;
    private final DishRepository dishRepository;
    private final OrderRepository orderRepository;
    private final OrderItemRepository orderItemRepository;

    public static void main(String[] args) {
        SpringApplication.run(GestionCmdApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        if (clientRepository.count() == 0 && dishRepository.count() == 0) {
            initializeData();
        }
    }

    private void initializeData() {
        // Ajout des Clients
        Client client1 = new Client(null, "Alice Dupont", "alice@example.com", "0601020304", null);
        Client client2 = new Client(null, "Bob Martin", "bob@example.com", "0611121314", null);
        Client client3 = new Client(null, "Charlie Durand", "charlie@example.com", "0621222324", null);
        clientRepository.saveAll(List.of(client1, client2, client3));

        // Ajout des Plats
        Dish dish1 = new Dish(null, "Pizza Margherita", 12.50, true);
        Dish dish2 = new Dish(null, "Burger Classic", 10.00, true);
        Dish dish3 = new Dish(null, "Salade César", 8.50, true);
        Dish dish4 = new Dish(null, "Pâtes Carbonara", 13.00, true);
        Dish dish5 = new Dish(null, "Tiramisu", 5.50, true);
        dishRepository.saveAll(List.of(dish1, dish2, dish3, dish4, dish5));

        // Ajout des Commandes
        Order order1 = new Order(null, client1, java.time.LocalDateTime.now(), 0, null);
        Order order2 = new Order(null, client2, java.time.LocalDateTime.now(), 0, null);

        OrderItem item1 = new OrderItem(null, order1, dish1, 2);
        OrderItem item2 = new OrderItem(null, order1, dish3, 1);
        OrderItem item3 = new OrderItem(null, order2, dish2, 1);
        OrderItem item4 = new OrderItem(null, order2, dish4, 2);

        double total1 = (dish1.getPrice() * 2) + (dish3.getPrice());
        double total2 = (dish2.getPrice()) + (dish4.getPrice() * 2);

        order1.setItems(List.of(item1, item2));
        order1.setTotalPrice(total1);
        order2.setItems(List.of(item3, item4));
        order2.setTotalPrice(total2);

        orderRepository.saveAll(List.of(order1, order2));
        orderItemRepository.saveAll(List.of(item1, item2, item3, item4));

        System.out.println("✅ Données initiales insérées avec succès !");
    }
}
