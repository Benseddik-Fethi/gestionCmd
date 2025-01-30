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

import java.util.List;
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
    void testGetAllDishes() {
        when(dishRepository.findAll()).thenReturn(List.of(dish));

        List<DishDTO> dishes = dishService.getAllDishes();

        assertFalse(dishes.isEmpty());
        assertEquals(1, dishes.size());
        assertEquals("Pizza Margherita", dishes.get(0).name());
    }

    @Test
    void testGetDishById_Success() {
        when(dishRepository.findById(dishId)).thenReturn(Optional.of(dish));

        DishDTO result = dishService.getDishById(dishId);

        assertNotNull(result);
        assertEquals(dish.getId(), result.id());
    }

    @Test
    void testCreateDish() {
        DishDTO dishDTO = new DishDTO(null, "Burger Classic", 10.00, true);
        Dish newDish = new Dish(UUID.randomUUID(), dishDTO.name(), dishDTO.price(), dishDTO.available());

        when(dishRepository.save(any(Dish.class))).thenReturn(newDish);

        DishDTO result = dishService.createDish(dishDTO);

        assertNotNull(result);
        assertEquals("Burger Classic", result.name());
        assertEquals(10.00, result.price());
        verify(dishRepository, times(1)).save(any(Dish.class));
    }

    @Test
    void testDeleteDish_Success() {
        when(dishRepository.findById(dishId)).thenReturn(Optional.of(dish));
        doNothing().when(dishRepository).delete(dish);

        assertDoesNotThrow(() -> dishService.deleteDish(dishId));

        verify(dishRepository, times(1)).delete(dish);
    }

    @Test
    void testDeleteDish_NotFound() {
        when(dishRepository.findById(dishId)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> dishService.deleteDish(dishId));
    }

    @Test
    void testGetAvailableDishes() {
        when(dishRepository.findByAvailableTrue()).thenReturn(List.of(dish));

        List<DishDTO> result = dishService.getAvailableDishes();

        assertFalse(result.isEmpty());
        assertEquals(1, result.size());
        assertTrue(result.get(0).available());
    }
}
