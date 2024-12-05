package com.smtaste.restaurant.service;

import com.smtaste.restaurant.dto.ProductoCarritoDto;
import com.smtaste.restaurant.dto.ProductoMenuResponse;
import com.smtaste.restaurant.model.Producto;
import com.smtaste.restaurant.repository.ProductoRepository;

import org.hibernate.validator.constraints.ModCheck;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.List;
import java.util.Optional;

public class ProductoServiceTest {
    
    @Mock
    private ProductoRepository productoRepository;

    @InjectMocks
    private ProductoService productoService;

    @BeforeEach
    void setUp(){
        MockitoAnnotations.openMocks(this);
    }

    /*@Test
    void testFindAll(){
        List<String> productosId = List.of("1", "2", "3");
        List<Long> productosIdLong = List.of(1L, 2L, 3L);
        List<Object[]> productosCrudo = List.of(
            new Object[]{1, "Producto 1", "Descripcion 1", "url1", "categoria1", 100.0f},
            new Object[]{2, "Producto 2", "Descripcion 2", "url2", "categoria2", 200.0f}
                );
        
        when(productoRepository.findAllProductoCarrito(productosIdLong)).thenReturn(productosCrudo);

        List<ProductoCarritoDto> result = productoService.findAll(productosId);

        assertEquals(2, result.size());
        assertEquals("Producto 1", result.get(0).nombre());
        verify(productoRepository, times(1)).findAllProductoCarrito(productosIdLong);
    }*/

    @Test
    void testFindAllProductosMenu() {
        List<Object[]> productos = List.of(
                new Object[]{1, "Producto 1", "Descripcion 1", "url1", 10, 100.0f},
                new Object[]{2, "Producto 2", "Descripcion 2", "url2", 20, 200.0f}
        );

        when(productoRepository.findAllProductosMenu()).thenReturn(productos);

        List<ProductoMenuResponse> result = productoService.findAllProductosMenu();

        assertEquals(2, result.size());
        assertEquals(10, result.get(0).cantidad());
        verify(productoRepository, times(1)).findAllProductosMenu();
    }

    @Test
    void testSaveProducto() {
        ProductoMenuResponse productoMenuResponse = new ProductoMenuResponse(
                1, "Nuevo Producto", "Desc", "url1", 10, 50.0f);

        Producto productoGuardado = new Producto();
        productoGuardado.setId(1);
        productoGuardado.setNombre("Nuevo Producto");

        when(productoRepository.save(any(Producto.class))).thenReturn(productoGuardado);

        Producto result = productoService.saveProducto(productoMenuResponse);

        assertEquals("Nuevo Producto", result.getNombre());
        verify(productoRepository, times(1)).save(any(Producto.class));
    }

    @Test
    void testUpdateProducto() {
        Long productoId = 1L;
        ProductoMenuResponse productoDetails = new ProductoMenuResponse(
                1, "Producto Actualizado", "Nueva Desc", "url2", 5, 75.0f);

        Producto productoExistente = new Producto();
        productoExistente.setId(1);
        productoExistente.setNombre("Producto Original");

        when(productoRepository.findById(productoId)).thenReturn(Optional.of(productoExistente));
        when(productoRepository.save(any(Producto.class))).thenReturn(productoExistente);

        Producto result = productoService.updateProducto(productoId, productoDetails);

        assertEquals("Producto Actualizado", result.getNombre());
        verify(productoRepository, times(1)).findById(productoId);
        verify(productoRepository, times(1)).save(any(Producto.class));
    }

    @Test
    void testDeleteProducto() {
        Long productoId = 1L;

        doNothing().when(productoRepository).deleteById(productoId);

        productoService.deleteProducto(productoId);

        verify(productoRepository, times(1)).deleteById(productoId);
    }

}
