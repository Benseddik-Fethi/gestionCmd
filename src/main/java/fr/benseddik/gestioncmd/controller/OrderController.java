package fr.benseddik.gestioncmd.controller;

import fr.benseddik.gestioncmd.dto.OrderRequestDTO;
import fr.benseddik.gestioncmd.dto.OrderResponseDTO;
import fr.benseddik.gestioncmd.exception.BusinessLogicException;
import fr.benseddik.gestioncmd.exception.ResourceNotFoundException;
import fr.benseddik.gestioncmd.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;
@RestController
@RequestMapping("/orders")
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;

    @PostMapping
    public ResponseEntity<OrderResponseDTO> placeOrder(@RequestBody OrderRequestDTO orderRequestDTO) {
        OrderResponseDTO orderResponseDTO = orderService.placeOrder(orderRequestDTO);
        return ResponseEntity.ok(orderResponseDTO);
    }
}

