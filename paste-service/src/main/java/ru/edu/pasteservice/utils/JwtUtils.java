package ru.edu.pasteservice.utils;

import jakarta.servlet.http.HttpServletRequest;

import java.util.Optional;

public class JwtUtils {
    public static Optional<String> extractJwt(HttpServletRequest request) {
        String authHeader = request.getHeader("Authorization");
        if(authHeader == null || !authHeader.startsWith("Bearer ")) {
            return Optional.empty();
        }
        return Optional.of(authHeader.substring(7));
    }
}
