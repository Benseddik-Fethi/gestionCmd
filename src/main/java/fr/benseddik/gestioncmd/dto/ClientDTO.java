package fr.benseddik.gestioncmd.dto;


import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import java.util.UUID;

public record ClientDTO(
        UUID id,

        @NotBlank(message = "Le nom est obligatoire")
        String name,

        @Email(message = "L'email doit être valide")
        String email,

        String phone
) {}

