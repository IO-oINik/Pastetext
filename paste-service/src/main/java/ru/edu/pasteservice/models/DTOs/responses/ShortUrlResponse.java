package ru.edu.pasteservice.models.DTOs.responses;


import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@NoArgsConstructor
@Setter
@Getter
public class ShortUrlResponse {
        private Long id;
        private String url;
}
