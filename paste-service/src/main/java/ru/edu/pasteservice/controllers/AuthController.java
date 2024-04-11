package ru.edu.pasteservice.controllers;

import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.edu.pasteservice.models.DTOs.requests.AuthenticateRequest;
import ru.edu.pasteservice.models.DTOs.requests.RegisterRequest;
import ru.edu.pasteservice.models.DTOs.responses.AuthenticationResponse;
import ru.edu.pasteservice.services.AuthService;


@RequiredArgsConstructor
@RequestMapping("/api/auth")
@RestController
public class AuthController {
    private final AuthService authService;

    @Operation(summary = "Регистрация пользователя")
    @PostMapping("/registration")
    public ResponseEntity<AuthenticationResponse> registration(@Valid @RequestBody RegisterRequest request) {
        return ResponseEntity.ok(authService.registration(request));
    }

    @Operation(summary = "Получения токена пользователя")
    @PostMapping("/authenticate")
    public ResponseEntity<AuthenticationResponse> authenticate(@Valid @RequestBody AuthenticateRequest request) {
        return ResponseEntity.ok(authService.authenticate(request));
    }
}
