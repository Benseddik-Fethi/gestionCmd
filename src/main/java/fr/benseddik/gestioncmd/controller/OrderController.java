package fr.benseddik.gestioncmd.controller;

import fr.benseddik.gestioncmd.dto.OrderRequestDTO;
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
    public ResponseEntity<?> placeOrder(@RequestBody OrderRequestDTO orderRequestDTO) {
        try {
            return ResponseEntity.ok(orderService.placeOrder(orderRequestDTO));
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(404).body(e.getMessage());
        } catch (BusinessLogicException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body("Une erreur interne s'est produite");
        }
    }
}
