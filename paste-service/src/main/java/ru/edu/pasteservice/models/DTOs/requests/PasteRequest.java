package ru.edu.pasteservice.models.DTOs.requests;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@NoArgsConstructor
@Setter
@Getter
public class PasteRequest {
    @NotBlank
    private String title;

    @NotBlank
    private String content;

    @NotBlank
    private String visibility;

    @NotNull
    private int pasteExpirationMinutes;

    private List<String> permissions;
}
