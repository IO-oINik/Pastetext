package ru.edu.pasteservice.models.DTOs.responses;


import lombok.*;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class CreatedPasteResponse {
    private String url;
    private String title;
}
