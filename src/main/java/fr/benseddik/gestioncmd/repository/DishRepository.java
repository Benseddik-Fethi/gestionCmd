package fr.benseddik.gestioncmd.repository;

import fr.benseddik.gestioncmd.domain.Dish;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface DishRepository extends JpaRepository<Dish, UUID> {
    List<Dish> findByAvailableTrue(); // Récupérer uniquement les plats disponibles
}

