package ru.edu.pasteservice.controllers;

import io.swagger.v3.oas.annotations.Operation;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.edu.pasteservice.models.DTOs.requests.AuthenticateRequest;
import ru.edu.pasteservice.models.DTOs.requests.RegisterRequest;
import ru.edu.pasteservice.models.DTOs.responses.AuthenticationResponse;
import ru.edu.pasteservice.models.DTOs.responses.MessageResponse;
import ru.edu.pasteservice.services.AuthService;
import ru.edu.pasteservice.utils.JwtUtils;


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

    @Operation(summary = "Разлогиниться")
    @GetMapping("/logout")
    public ResponseEntity<MessageResponse> logout(HttpServletRequest request) {
        String token = JwtUtils.extractJwt(request).orElseThrow();
        authService.logout(token);
        return ResponseEntity.ok(new MessageResponse("successfully logged out"));
    }
}
