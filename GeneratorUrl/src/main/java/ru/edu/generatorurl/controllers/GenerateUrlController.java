package ru.edu.generatorurl.controllers;

import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.edu.generatorurl.exceptions.GenerateUrlException;
import ru.edu.generatorurl.models.DTOs.MessageDto;
import ru.edu.generatorurl.models.ShortUrl;
import ru.edu.generatorurl.services.GenerateUrlService;

@RequiredArgsConstructor
@RequestMapping("/api")
@RestController
public class GenerateUrlController {
    private final GenerateUrlService generateUrlService;

    @Operation(summary = "Генерирует уникальный url")
    @GetMapping("/generated-url")
    public ResponseEntity<ShortUrl> generateUrl() throws GenerateUrlException {
        return ResponseEntity.ok(generateUrlService.generateUrl());
    }

    @Operation(summary = "Удалить сгенерированный url")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("/generated-url")
    public ResponseEntity<MessageDto> deleteUrl(@RequestBody ShortUrl shortUrl) {
        generateUrlService.deleteUrlById(shortUrl);
        return ResponseEntity.status(HttpStatus.valueOf(204))
                .body(new MessageDto("URL deleted successfully"));
    }
}
