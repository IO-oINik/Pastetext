package ru.edu.pasteservice.models.DTOs.responses;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import ru.edu.pasteservice.models.Visibility;

import java.time.LocalDateTime;

@NoArgsConstructor
@Setter
@Getter
public class PasteResponse {
    private String title;
    private String content;
    private LocalDateTime createdAt;
    private LocalDateTime expiredAt;
    private Visibility visibility;
    private LocalDateTime lastVisited;
    private String author;
}
