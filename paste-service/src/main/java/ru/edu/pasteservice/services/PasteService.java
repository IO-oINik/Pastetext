package ru.edu.pasteservice.services;

import io.minio.errors.MinioException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import ru.edu.pasteservice.exceptions.NoAccessException;
import ru.edu.pasteservice.exceptions.PasteIsExpiredException;
import ru.edu.pasteservice.exceptions.PasteNotFoundException;
import ru.edu.pasteservice.mappers.PasteMapper;
import ru.edu.pasteservice.models.DTOs.requests.PasteRequest;
import ru.edu.pasteservice.models.DTOs.responses.CreatedPasteResponse;
import ru.edu.pasteservice.models.DTOs.responses.PasteResponse;
import ru.edu.pasteservice.models.DTOs.responses.ShortUrlResponse;
import ru.edu.pasteservice.models.PasteMeta;
import ru.edu.pasteservice.models.User;
import ru.edu.pasteservice.models.Visibility;
import ru.edu.pasteservice.repositories.ContentRepository;
import ru.edu.pasteservice.repositories.PasteRepository;
import ru.edu.pasteservice.repositories.UserRepository;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class PasteService {
    private final PasteRepository pasteRepository;
    private final UserRepository userRepository;
    private final GeneratorUrlService generatorUrlService;
    private final ContentRepository contentRepository;
    private final PasteMapper pasteMapper;

    public PasteResponse getPaste(String id, UserDetails userDetails) throws PasteNotFoundException, PasteIsExpiredException, NoAccessException, MinioException, IOException {
        PasteMeta pasteMeta = pasteRepository.findById(id).orElseThrow(() -> new PasteNotFoundException("PasteMeta not found"));
        if(pasteMeta.getExpiredAt().isBefore(LocalDateTime.now())) {
            throw new PasteIsExpiredException("PasteMeta is expired");
        }
        if(pasteMeta.getVisibility() == Visibility.PRIVATE) {
            User user = userRepository.findByUsername(userDetails.getUsername()).orElseThrow();
            if(!pasteMeta.getPermissions().contains(user)) {
                throw new NoAccessException("There is no access to this pasteMeta");
            }
        }

        String content = contentRepository.getContent("pastes", id);
        PasteResponse pasteResponse = pasteMapper.toResponse(pasteMeta);
        pasteResponse.setContent(content);
        return pasteResponse;
    }

    public CreatedPasteResponse createPaste(PasteRequest pasteRequest, UserDetails userDetails) throws MinioException {
        User user = userRepository.findByUsername(userDetails.getUsername()).orElseThrow();
        PasteMeta pasteMeta = new PasteMeta();
        pasteMeta.setTitle(pasteRequest.getTitle());
        pasteMeta.setOwner(user);
        pasteMeta.setExpiredAt(LocalDateTime.now().plusMinutes(pasteRequest.getPasteExpirationMinutes()));
        pasteMeta.setCreatedAt(LocalDateTime.now());

        ShortUrlResponse shortUrlResponse = generatorUrlService.generateShortUrl();
        pasteMeta.setId(shortUrlResponse.getUrl());

        if(pasteRequest.getVisibility().equalsIgnoreCase("public")) {
            pasteMeta.setVisibility(Visibility.PUBLIC);
        } else {
            pasteMeta.setVisibility(Visibility.PRIVATE);
            for(var userPermission : pasteRequest.getPermissions()) {
                Optional<User> userOptional = userRepository.findByUsername(userPermission);
                userOptional.ifPresent(value -> pasteMeta.getPermissions().add(value));
            }
        }
        pasteRepository.save(pasteMeta);
        contentRepository.putContent("pastes", pasteMeta.getId(), pasteRequest.getContent());

        return CreatedPasteResponse.builder().url(pasteMeta.getId()).title(pasteMeta.getTitle()).build();
    }

    public void deletePaste(String id, UserDetails userDetails) throws PasteNotFoundException, NoAccessException, MinioException {
        PasteMeta pasteMeta = pasteRepository.findById(id).orElseThrow(() -> new PasteNotFoundException("PasteMeta not found"));

        if(!pasteMeta.getOwner().getUsername().equals(userDetails.getUsername())) {
            throw new NoAccessException("There is no access to this pasteMeta");
        }
        contentRepository.deleteContent("pastes", pasteMeta.getId());
        pasteRepository.delete(pasteMeta);
    }

    public void updateLastVisited(String pasteId) throws PasteNotFoundException {
        PasteMeta pasteMeta = pasteRepository.findById(pasteId).orElseThrow(() -> new PasteNotFoundException("PasteMeta not found"));
        pasteMeta.setLastVisited(LocalDateTime.now());
        pasteRepository.save(pasteMeta);
    }
}
