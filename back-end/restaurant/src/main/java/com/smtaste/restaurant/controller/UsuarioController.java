package com.smtaste.restaurant.controller;

import com.smtaste.restaurant.model.Usuario;
import com.smtaste.restaurant.service.UsuarioService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/usuarios")
@Slf4j
public class UsuarioController {
    private final UsuarioService usuarioService;

    public UsuarioController(UsuarioService usuarioService) {
        this.usuarioService = usuarioService;
    }

    @PostMapping
    public ResponseEntity<Usuario> addUsuario(@RequestBody Usuario usuario) {
        log.info("Agregando nuevo usuario con email: {}", usuario.getEmail());
        Usuario newUsuario = usuarioService.saveUsuario(usuario);
        return ResponseEntity.status(HttpStatus.CREATED).body(newUsuario);
    }

    @GetMapping
    public ResponseEntity<Map<String, String>> getUsernameByLogin(@RequestParam String email, @RequestParam String contrasena) {
        log.info("Intento de inicio de sesión con email: {}", email);

        String usuarioNombre = usuarioService.getUsuario(email, contrasena);
        Map<String, String> response = new HashMap<>();

        if (usuarioNombre == null) {
            response.put("message", "Usuario no encontrado");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }

        response.put("user", usuarioNombre);
        return ResponseEntity.ok(response);
    }
}