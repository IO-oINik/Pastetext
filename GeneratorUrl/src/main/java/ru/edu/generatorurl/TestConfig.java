package ru.edu.generatorurl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import ru.edu.generatorurl.exceptions.GenerateUrlException;
import ru.edu.generatorurl.services.GenerateUrlService;

@Slf4j
@RequiredArgsConstructor
@Configuration
public class TestConfig {
    private final GenerateUrlService generateUrlService;

    public CommandLineRunner commandLineRunner() throws GenerateUrlException {
        for(int i = 0; i < 1_000; ++i) {
            generateUrlService.generateUrl();
            log.info("URL generated ID=" + i);
        }

        return args -> System.out.println("Created 100_000 urls");
    }
}
