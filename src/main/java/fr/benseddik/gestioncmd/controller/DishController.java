package fr.benseddik.gestioncmd.controller;

import fr.benseddik.gestioncmd.dto.DishDTO;
import fr.benseddik.gestioncmd.exception.ResourceNotFoundException;
import fr.benseddik.gestioncmd.service.DishService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/dishes")
@RequiredArgsConstructor
public class DishController {

    private final DishService dishService;


    @PostMapping
    public ResponseEntity<DishDTO> createDish(@Valid @RequestBody DishDTO dishDTO) {
        return ResponseEntity.ok(dishService.createDish(dishDTO));
    }

    @GetMapping
    public ResponseEntity<List<DishDTO>> getAllDishes() {
        return ResponseEntity.ok(dishService.getAllDishes());
    }

    @GetMapping("/available")
    public ResponseEntity<List<DishDTO>> getAvailableDishes() {
        return ResponseEntity.ok(dishService.getAvailableDishes());
    }

    @GetMapping("/{id}")
    public ResponseEntity<DishDTO> getDishById(@PathVariable UUID id) {
        try {
            return ResponseEntity.ok(dishService.getDishById(id));
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteDish(@PathVariable UUID id) {
        try {
            dishService.deleteDish(id);
            return ResponseEntity.noContent().build();
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }
}

