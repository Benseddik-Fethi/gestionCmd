package fr.benseddik.gestioncmd.dto;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

public record OrderResponseDTO(
        UUID id,
        UUID clientId,
        LocalDateTime orderDate,
        double totalPrice,
        List<OrderItemDTO> items
) {}
