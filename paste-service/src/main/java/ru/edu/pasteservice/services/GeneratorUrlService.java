package ru.edu.pasteservice.services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import ru.edu.pasteservice.models.DTOs.responses.ShortUrlResponse;

@RequiredArgsConstructor
@Service
public class GeneratorUrlService {
    private final RestTemplate restTemplate;

    public ShortUrlResponse generateShortUrl() {
        return restTemplate.getForObject("http://GeneratorUrl/api/generated-url", ShortUrlResponse.class);
    }
}
