package ru.edu.pasteservice.services;

import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import ru.edu.pasteservice.models.DTOs.requests.AuthenticateRequest;
import ru.edu.pasteservice.models.DTOs.requests.RegisterRequest;
import ru.edu.pasteservice.models.DTOs.responses.AuthenticationResponse;
import ru.edu.pasteservice.models.Role;
import ru.edu.pasteservice.models.User;
import ru.edu.pasteservice.repositories.UserRepository;

@RequiredArgsConstructor
@Service
public class AuthService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;


    public AuthenticationResponse registration(RegisterRequest registerRequest) {
        User user = User.builder()
                .username(registerRequest.getUsername())
                .password(passwordEncoder.encode(registerRequest.getPassword()))
                .email(registerRequest.getEmail())
                .role(Role.USER)
                .build();
        userRepository.save(user);
        return new AuthenticationResponse(jwtService.generateJwt(user));
    }

    public AuthenticationResponse authenticate(AuthenticateRequest authenticateRequest) {
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                authenticateRequest.getUsername(),
                authenticateRequest.getPassword()
        ));
        User user = userRepository.findByUsername(authenticateRequest.getUsername()).orElseThrow();
        return new AuthenticationResponse(jwtService.generateJwt(user));
    }
}
