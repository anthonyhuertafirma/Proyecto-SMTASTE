package com.smtaste.restaurant.controller;

import com.smtaste.restaurant.dto.ProductoMenuResponse;
import com.smtaste.restaurant.model.Producto;
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

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.hamcrest.Matchers.*;

@WebMvcTest(ProductoController.class)
class ProductoControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ProductoService productoService;

    @BeforeEach
    void setUp() {
        // Aquí no es necesario MockitoAnnotations.openMocks(this), porque Spring Boot ya maneja la inyección
        mockMvc = MockMvcBuilders.standaloneSetup(new ProductoController(productoService)).build();
    }

    @Test
    void testGetProductos() throws Exception {
        ProductoMenuResponse productoResponse = new ProductoMenuResponse(
                1, "Pizza", "http://example.com/pizza.jpg",
                "Deliciosa pizza de queso", 10, 12.99f
        );

        List<ProductoMenuResponse> productos = List.of(productoResponse);
        when(productoService.findAllProductosMenu()).thenReturn(productos);

        mockMvc.perform(get("/api/productos"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].id", is(1)))
                .andExpect(jsonPath("$[0].nombre", is("Pizza")));

        verify(productoService, times(1)).findAllProductosMenu();
    }

    @Test
    void testAddProducto() throws Exception {
        Producto producto = new Producto();
        producto.setId(1);
        producto.setNombre("Pizza");
    
        when(productoService.saveProducto(any(ProductoMenuResponse.class))).thenReturn(producto);

        mockMvc.perform(post("/api/productos")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"nombre\":\"Pizza\", \"descripcion\":\"Deliciosa pizza\", \"precio\": 12.99, \"cantidad\": 10, \"url_foto\":\"http://example.com/pizza.jpg\"}"))
                .andExpect(status().isCreated())  // Expect 201 Created
                .andExpect(jsonPath("$.id", is(1)))
                .andExpect(jsonPath("$.nombre", is("Pizza")));
    
        verify(productoService, times(1)).saveProducto(any(ProductoMenuResponse.class));
    }
    


    @Test
    void testUpdateProducto() throws Exception {
        Producto producto = new Producto();
        producto.setId(1);
        producto.setNombre("Pizza Actualizada");
    
        ProductoMenuResponse productoDetails = new ProductoMenuResponse(
                1, "Pizza Actualizada", "http://example.com/pizza-updated.jpg",
                "Pizza con ingredientes nuevos", 15, 14.99f
        );
    
        when(productoService.updateProducto(anyLong(), any(ProductoMenuResponse.class))).thenReturn(producto);

        mockMvc.perform(put("/api/productos/1")  // Use PUT for update
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"nombre\":\"Pizza Actualizada\", \"descripcion\":\"Pizza con ingredientes nuevos\", \"precio\": 14.99, \"cantidad\": 15, \"url_foto\":\"http://example.com/pizza-updated.jpg\"}"))
                .andExpect(status().isOk())  // Expect 200 OK for successful update
                .andExpect(jsonPath("$.id", is(1)))
                .andExpect(jsonPath("$.nombre", is("Pizza Actualizada")));
    
        verify(productoService, times(1)).updateProducto(anyLong(), any(ProductoMenuResponse.class));
    }
    


    @Test
    void testDeleteProducto() throws Exception {
        doNothing().when(productoService).deleteProducto(anyLong());

        mockMvc.perform(delete("/api/productos/1"))
                .andExpect(status().isNoContent());  // Expect 204 No Content

        verify(productoService, times(1)).deleteProducto(anyLong());
    }
}
