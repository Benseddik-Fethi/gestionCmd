package fr.benseddik.gestioncmd.domain;


import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

import java.util.UUID;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Dish {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @NotBlank(message = "Le nom du plat est obligatoire")
    private String name;

    @Min(value = 0, message = "Le prix doit être supérieur ou égal à 0")
    private double price;

    private boolean available = true;
}

