package ru.edu.pasteservice.models.DTOs.requests;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Setter
@Getter
public class AuthenticateRequest {
    @NotBlank
    private String username;
    @NotBlank
    private String password;
}
