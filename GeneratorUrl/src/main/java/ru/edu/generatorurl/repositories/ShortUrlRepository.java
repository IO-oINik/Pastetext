package ru.edu.generatorurl.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.edu.generatorurl.models.ShortUrl;

public interface ShortUrlRepository extends JpaRepository<ShortUrl, Long> {
    boolean existsByUrl(String url);
}
