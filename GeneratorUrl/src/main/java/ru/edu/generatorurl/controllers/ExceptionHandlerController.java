package ru.edu.generatorurl.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;
import ru.edu.generatorurl.exceptions.GenerateUrlException;
import ru.edu.generatorurl.models.DTOs.MessageDto;

@RestControllerAdvice
public class ExceptionHandlerController extends ResponseEntityExceptionHandler {

    @ExceptionHandler({GenerateUrlException.class})
    public ResponseEntity<MessageDto> generateUrlExceptionHandler(GenerateUrlException exception) {
        return ResponseEntity.status(HttpStatus.GATEWAY_TIMEOUT).body(new MessageDto(exception.getMessage()));
    }
}
