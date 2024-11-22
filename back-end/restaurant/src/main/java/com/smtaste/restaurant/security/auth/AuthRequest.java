package com.smtaste.restaurant.security.auth;

public record AuthRequest(
        String email,
        String contrasena
) {
}
