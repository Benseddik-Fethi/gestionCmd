package fr.benseddik.gestioncmd.service;

import fr.benseddik.gestioncmd.domain.Dish;
import fr.benseddik.gestioncmd.dto.DishDTO;
import fr.benseddik.gestioncmd.exception.ResourceNotFoundException;
import fr.benseddik.gestioncmd.repository.DishRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class DishService {

    private final DishRepository dishRepository;


    public DishDTO createDish(DishDTO dishDTO) {
        Dish dish = new Dish(null, dishDTO.name(), dishDTO.price(), dishDTO.available());
        Dish savedDish = dishRepository.save(dish);
        return new DishDTO(savedDish.getId(), savedDish.getName(), savedDish.getPrice(), savedDish.isAvailable());
    }

    public List<DishDTO> getAllDishes() {
        return dishRepository.findAll()
                .stream()
                .map(dish -> new DishDTO(dish.getId(), dish.getName(), dish.getPrice(), dish.isAvailable()))
                .toList();
    }

    public DishDTO getDishById(UUID id) {
        Dish dish = dishRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Plat avec ID " + id + " non trouvé"));
        return new DishDTO(dish.getId(), dish.getName(), dish.getPrice(), dish.isAvailable());
    }

    public void deleteDish(UUID id) {
        Dish dish = dishRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Plat avec ID " + id + " non trouvé"));
        dishRepository.delete(dish);
    }

    public List<DishDTO> getAvailableDishes() {
        return dishRepository.findByAvailableTrue()
                .stream()
                .map(dish -> new DishDTO(dish.getId(), dish.getName(), dish.getPrice(), dish.isAvailable()))
                .toList();
    }
}
