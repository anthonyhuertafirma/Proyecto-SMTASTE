package com.smtaste.restaurant.controller;

import com.smtaste.restaurant.dto.ProductoCarritoDto;
import com.smtaste.restaurant.service.ProductoService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.List;

import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.hamcrest.Matchers.*;

@WebMvcTest(CarritoController.class)
class CarritoControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ProductoService productoService;

    @BeforeEach
    void setUp() {
        // No es necesario inicializar MockMvc manualmente cuando usas @WebMvcTest
    }

    @Test
    void testGetItemsConProductos() throws Exception {
        // Configuración de ProductoCarritoDto de prueba con parámetros correctos
        ProductoCarritoDto productoCarritoDto = new ProductoCarritoDto(
                1, "Pizza", "http://example.com/pizza.jpg", 
                "Deliciosa pizza de queso", "Pizzeria Italiana", 12.99f
        );

        // Mockeo del servicio
        List<ProductoCarritoDto> productoCarrito = List.of(productoCarritoDto);
        when(productoService.findAll(anyList())).thenReturn(productoCarrito);

        // Prueba de la solicitud
        mockMvc.perform(post("/api/carrito")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("[\"1\"]"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].id", is(1)))
                .andExpect(jsonPath("$[0].nombre", is("Pizza")))
                .andExpect(jsonPath("$[0].urlImagen", is("http://example.com/pizza.jpg")))
                .andExpect(jsonPath("$[0].descripcion", is("Deliciosa pizza de queso")))
                .andExpect(jsonPath("$[0].nombreRestaurante", is("Pizzeria Italiana")))
                .andExpect(jsonPath("$[0].precio", is(12.99)));

        verify(productoService, times(1)).findAll(anyList());
    }

    @Test
    void testGetItemsSinProductos() throws Exception {
        // Mockeo del servicio para devolver una lista vacía
        when(productoService.findAll(anyList())).thenReturn(List.of());

        // Prueba de la solicitud
        mockMvc.perform(post("/api/carrito")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("[\"2\"]"))
                .andExpect(status().isNoContent());

        verify(productoService, times(1)).findAll(anyList());
    }
}
