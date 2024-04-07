package ru.edu.generatorurl.services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.unbrokendome.base62.Base62;
import ru.edu.generatorurl.exceptions.GenerateUrlException;
import ru.edu.generatorurl.models.ShortUrl;
import ru.edu.generatorurl.repositories.ShortUrlRepository;

import java.util.UUID;

@RequiredArgsConstructor
@Service
public class GenerateUrlService {
    private final ShortUrlRepository urlRepository;

    /**
     * Генерирует уникальный url и сохраняет в БД
     * @return уникальный url
     * @throws GenerateUrlException превышено колличество попыток генерации url
     */
    public ShortUrl generateUrl() throws GenerateUrlException {
        UUID uuid = UUID.randomUUID();
        String url;
        int i = 0;
        do {
            if(i > 5) {
                throw new GenerateUrlException("Превышенно колличество попыток генерации url");
            }
            url = Base62.encodeUUID(uuid).substring(0, 11);
            ++i;
        } while (urlRepository.existsByUrl(url));

        ShortUrl shortUrl = new ShortUrl(url);
        urlRepository.saveAndFlush(shortUrl);
        return shortUrl;
    }

    /**
     * Удаляет из БД url
     * @param shortUrl
     */
    public void deleteUrlById(ShortUrl shortUrl) {
        urlRepository.delete(shortUrl);
    }
}
