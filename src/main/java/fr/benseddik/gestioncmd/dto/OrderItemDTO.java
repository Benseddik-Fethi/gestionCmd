package fr.benseddik.gestioncmd.dto;


import jakarta.validation.constraints.Min;
import java.util.UUID;

public record OrderItemDTO(
        UUID dishId,

        @Min(value = 1, message = "La quantité doit être d'au moins 1")
        int quantity
) {}
