package fr.benseddik.gestioncmd.controller;

import fr.benseddik.gestioncmd.dto.OrderItemDTO;
import fr.benseddik.gestioncmd.dto.OrderRequestDTO;
import fr.benseddik.gestioncmd.dto.OrderResponseDTO;
import fr.benseddik.gestioncmd.service.OrderService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.List;
import java.util.UUID;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

class OrderControllerTest {

    private MockMvc mockMvc;

    @Mock
    private OrderService orderService;

    @InjectMocks
    private OrderController orderController;

    private UUID clientId;
    private UUID dishId;
    private OrderRequestDTO orderRequestDTO;
    private OrderResponseDTO orderResponseDTO;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(orderController).build();

        clientId = UUID.randomUUID();
        dishId = UUID.randomUUID();
        orderRequestDTO = new OrderRequestDTO(clientId, List.of(new OrderItemDTO(dishId, 2)));
        orderResponseDTO = new OrderResponseDTO(UUID.randomUUID(), clientId, java.time.LocalDateTime.now(), 25.00, List.of(new OrderItemDTO(dishId, 2)));
    }

    @Test
    void testPlaceOrder() throws Exception {
        when(orderService.placeOrder(orderRequestDTO)).thenReturn(orderResponseDTO);

        mockMvc.perform(post("/orders")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"clientId\":\"" + clientId + "\",\"items\":[{\"dishId\":\"" + dishId + "\",\"quantity\":2}]}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.totalPrice").value(25.00));
    }
}
