package ru.edu.pasteservice.controllers;

import io.minio.errors.MinioException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import ru.edu.pasteservice.exceptions.NoAccessException;
import ru.edu.pasteservice.exceptions.PasteIsExpiredException;
import ru.edu.pasteservice.exceptions.PasteNotFoundException;
import ru.edu.pasteservice.models.DTOs.requests.PasteRequest;
import ru.edu.pasteservice.models.DTOs.responses.CreatedPasteResponse;
import ru.edu.pasteservice.models.DTOs.responses.MessageResponse;
import ru.edu.pasteservice.models.DTOs.responses.PasteResponse;
import ru.edu.pasteservice.services.PasteService;

import java.io.IOException;


@RequiredArgsConstructor
@RequestMapping("/api/pastes")
@RestController
public class PasteController {
    private final PasteService pasteService;

    @GetMapping("/{paste-id}")
    public ResponseEntity<PasteResponse> getPaste(@PathVariable("paste-id") String pasteId) throws PasteNotFoundException, NoAccessException, PasteIsExpiredException, MinioException, IOException {
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        PasteResponse pasteResponse = pasteService.getPaste(pasteId, userDetails);
        pasteService.updateLastVisited(pasteId);
        return ResponseEntity.ok(pasteResponse);
    }

    @PostMapping("")
    public ResponseEntity<CreatedPasteResponse> createPaste(@RequestBody PasteRequest request) throws MinioException {
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return ResponseEntity.status(HttpStatus.CREATED).body(pasteService.createPaste(request, userDetails));
    }

    @DeleteMapping("/{paste-id}")
    public ResponseEntity<MessageResponse> deletePaste(@PathVariable("paste-id") String pasteId) throws PasteNotFoundException, NoAccessException, MinioException {
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        pasteService.deletePaste(pasteId, userDetails);
        return ResponseEntity.status(HttpStatusCode.valueOf(204)).body(new MessageResponse("The paste has been successfully removed"));
    }
}
