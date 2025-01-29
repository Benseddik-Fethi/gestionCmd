package fr.benseddik.gestioncmd.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import java.util.UUID;

public record DishDTO(
        UUID id,

        @NotBlank(message = "Le nom du plat est obligatoire")
        String name,

        @Min(value = 0, message = "Le prix doit être supérieur ou égal à 0")
        double price,

        boolean available
) {}

