package ru.edu.pasteservice.services;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import ru.edu.pasteservice.models.InvalidatedToken;
import ru.edu.pasteservice.repositories.InvalidatedTokenRepository;

import java.util.List;

@RequiredArgsConstructor
@Component
public class InvalidateTokenService {
    private final InvalidatedTokenRepository repository;
    private final JwtService jwtService;

    @Scheduled(cron = "@hourly")
    public void deleteExpiredTokens() {
        List<InvalidatedToken> list = repository.findAllByOrderByAddedAtAsc(PageRequest.ofSize(1000));
        for (InvalidatedToken invalidatedToken : list) {
            String token = invalidatedToken.getToken();
            if(jwtService.isJwtExpired(token)) {
                repository.delete(invalidatedToken);
            }
        }
    }
}
