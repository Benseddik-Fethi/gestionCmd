package fr.benseddik.gestioncmd.service;

import fr.benseddik.gestioncmd.domain.Dish;
import fr.benseddik.gestioncmd.dto.DishDTO;
import fr.benseddik.gestioncmd.exception.ResourceNotFoundException;
import fr.benseddik.gestioncmd.repository.DishRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class DishServiceTest {

    @Mock
    private DishRepository dishRepository;

    @InjectMocks
    private DishService dishService;

    private Dish dish;
    private UUID dishId;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        dishId = UUID.randomUUID();
        dish = new Dish(dishId, "Pizza Margherita", 12.50, true);
    }

    @Test
    void testGetDishById_Success() {
        when(dishRepository.findById(dishId)).thenReturn(Optional.of(dish));

        DishDTO result = dishService.getDishById(dishId);

        assertNotNull(result);
        assertEquals(dish.getId(), result.id());
        assertEquals(dish.getName(), result.name());
        assertEquals(dish.getPrice(), result.price());
    }

    @Test
    void testGetDishById_NotFound() {
        when(dishRepository.findById(dishId)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> dishService.getDishById(dishId));
    }
}

