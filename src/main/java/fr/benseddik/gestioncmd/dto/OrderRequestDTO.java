package fr.benseddik.gestioncmd.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Min;
import java.util.List;
import java.util.UUID;

public record OrderRequestDTO(
        UUID clientId,

        @NotEmpty(message = "Une commande doit contenir au moins un plat")
        List<OrderItemDTO> items
) {}

