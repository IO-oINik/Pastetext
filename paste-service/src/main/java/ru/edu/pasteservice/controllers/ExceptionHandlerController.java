package ru.edu.pasteservice.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;
import ru.edu.pasteservice.exceptions.NoAccessException;
import ru.edu.pasteservice.exceptions.PasteIsExpiredException;
import ru.edu.pasteservice.exceptions.PasteNotFoundException;
import ru.edu.pasteservice.models.DTOs.responses.MessageResponse;

@RestControllerAdvice
public class ExceptionHandlerController extends ResponseEntityExceptionHandler {

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler({PasteNotFoundException.class, PasteIsExpiredException.class})
    public ResponseEntity<MessageResponse> generateUrlExceptionHandler(PasteNotFoundException exception) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new MessageResponse(exception.getMessage()));
    }

    @ResponseStatus(HttpStatus.FORBIDDEN)
    @ExceptionHandler({NoAccessException.class})
    public ResponseEntity<MessageResponse> noAccessExceptionHandler(NoAccessException exception) {
        return ResponseEntity.status(HttpStatus.FORBIDDEN).body(new MessageResponse(exception.getMessage()));
    }
}
