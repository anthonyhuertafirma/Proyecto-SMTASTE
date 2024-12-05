package com.smtaste.restaurant.service;

import com.smtaste.restaurant.model.Usuario;
import com.smtaste.restaurant.repository.UsuarioRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UsuarioService {
    private final UsuarioRepository usuarioRepository;

    public UsuarioService(UsuarioRepository usuarioRepository) {
        this.usuarioRepository = usuarioRepository;
    }

    public Usuario saveUsuario(Usuario usuario) {
        if (usuario.getEmail() == null || usuario.getContrasena() == null) {
            throw new IllegalArgumentException("Email o contraseña no válidos.");
        }
        return usuarioRepository.save(usuario);
    }

    public String getUsuario(String email, String contrasena) {
        Optional<String> usuarioNombre = usuarioRepository.findByLogin(email, contrasena);
        return usuarioNombre.orElse(null);
    }
}