package com.smtaste.restaurant.service;

import com.smtaste.restaurant.repository.UsuarioRepository;
import com.smtaste.restaurant.security.auth.AuthRequest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.Mockito.when;

public class UsuarioServiceTest {

    @Mock
    private UsuarioRepository usuarioRepository;

    @InjectMocks
    private UsuarioService usuarioService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetUsuarioSuccess() {
        // Datos de prueba
        AuthRequest authRequest = new AuthRequest("test@example.com", "password123");
        String usuarioEsperado = "Juan Perez";

        // Mocking del repositorio
        when(usuarioRepository.findByLogin(authRequest.email(), authRequest.contrasena()))
                .thenReturn(Optional.of(usuarioEsperado));

        // Llamar al método y verificar resultado
        String resultado = usuarioService.getUsuario(authRequest);
        assertEquals(usuarioEsperado, resultado);
    }

    @Test
    void testGetUsuarioNoEncontrado() {
        // Datos de prueba
        AuthRequest authRequest = new AuthRequest("test@example.com", "password123");

        // Mocking del repositorio
        when(usuarioRepository.findByLogin(authRequest.email(), authRequest.contrasena()))
                .thenReturn(Optional.empty());

        // Llamar al método y verificar resultado
        String resultado = usuarioService.getUsuario(authRequest);
        assertNull(resultado, "Se esperaba que el resultado fuera null cuando el usuario no es encontrado");
    }
}

