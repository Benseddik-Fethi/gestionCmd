package fr.benseddik.gestioncmd.controller;

import fr.benseddik.gestioncmd.dto.DishDTO;
import fr.benseddik.gestioncmd.exception.ResourceNotFoundException;
import fr.benseddik.gestioncmd.service.DishService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.List;
import java.util.UUID;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

class DishControllerTest {

    private MockMvc mockMvc;

    @Mock
    private DishService dishService;

    @InjectMocks
    private DishController dishController;

    private UUID dishId;
    private DishDTO dishDTO;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(dishController).build();
        dishId = UUID.randomUUID();
        dishDTO = new DishDTO(dishId, "Pizza Margherita", 12.50, true);
    }

    @Test
    void testGetAllDishes() throws Exception {
        when(dishService.getAllDishes()).thenReturn(List.of(dishDTO));

        mockMvc.perform(get("/dishes"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(dishId.toString()))
                .andExpect(jsonPath("$[0].name").value("Pizza Margherita"));
    }

    @Test
    void testGetAvailableDishes() throws Exception {
        when(dishService.getAvailableDishes()).thenReturn(List.of(dishDTO));

        mockMvc.perform(get("/dishes/available"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].available").value(true));
    }

    @Test
    void testGetDishById_Success() throws Exception {
        when(dishService.getDishById(dishId)).thenReturn(dishDTO);

        mockMvc.perform(get("/dishes/{id}", dishId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(dishId.toString()))
                .andExpect(jsonPath("$.name").value("Pizza Margherita"));
    }

    @Test
    void testGetDishById_NotFound() throws Exception {
        when(dishService.getDishById(dishId)).thenThrow(new ResourceNotFoundException("Plat non trouvé"));

        mockMvc.perform(get("/dishes/{id}", dishId))
                .andExpect(status().isNotFound());
    }

    @Test
    void testCreateDish() throws Exception {
        when(dishService.createDish(any(DishDTO.class))).thenReturn(dishDTO);

        mockMvc.perform(post("/dishes")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"name\":\"Pizza Margherita\",\"price\":12.50,\"available\":true}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Pizza Margherita"))
                .andExpect(jsonPath("$.price").value(12.50));
    }

    @Test
    void testDeleteDish_Success() throws Exception {
        doNothing().when(dishService).deleteDish(dishId);

        mockMvc.perform(delete("/dishes/{id}", dishId))
                .andExpect(status().isNoContent());
    }

    @Test
    void testDeleteDish_NotFound() throws Exception {
        doThrow(new ResourceNotFoundException("Plat non trouvé"))
                .when(dishService)
                .deleteDish(dishId);
        mockMvc.perform(delete("/dishes/{id}", dishId))
                .andExpect(status().isNotFound());
    }

}
