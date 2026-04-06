package com.example.Synapse.exceptions;

import jakarta.persistence.EntityNotFoundException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.security.access.AccessDeniedException;


@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<String> handleAccessDenied (AccessDeniedException e) {
        return ResponseEntity
                .status(403)
                .body(e.getMessage() + "Access denied");
    }

    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<String> handleEntityNotFound (EntityNotFoundException e) {
        return ResponseEntity
                .status(404)
                .body(e.getMessage() + " - Entity not found");
    }

    }

