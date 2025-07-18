package com.hackathon.verification.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

import java.time.LocalDateTime;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Global exception handler for verification-related exceptions.
 */
@ControllerAdvice
public class VerificationExceptionHandler {

    /**
     * Handles VerificationException and returns an appropriate response.
     *
     * @param ex the exception
     * @param request the web request
     * @return a ResponseEntity with error details
     */
    @ExceptionHandler(VerificationException.class)
    public ResponseEntity<Object> handleVerificationException(VerificationException ex, WebRequest request) {
        Map<String, Object> body = new LinkedHashMap<>();
        body.put("timestamp", LocalDateTime.now());
        body.put("entityType", ex.getEntityType());
        body.put("identifier", ex.getIdentifier());
        body.put("errorCode", ex.getErrorCode());
        body.put("message", ex.getMessage());
        body.put("path", request.getDescription(false));
        
        HttpStatus status;
        switch (ex.getErrorCode()) {
            case "NOT_FOUND":
                status = HttpStatus.NOT_FOUND;
                break;
            case "ALREADY_EXISTS":
            case "INVALID_INPUT":
                status = HttpStatus.BAD_REQUEST;
                break;
            case "VERIFICATION_FAILED":
                status = HttpStatus.UNPROCESSABLE_ENTITY;
                break;
            default:
                status = HttpStatus.INTERNAL_SERVER_ERROR;
        }
        
        body.put("status", status.value());
        body.put("error", status.getReasonPhrase());
        
        return new ResponseEntity<>(body, status);
    }
    
    /**
     * Handles IllegalArgumentException and returns a BAD_REQUEST response.
     *
     * @param ex the exception
     * @param request the web request
     * @return a ResponseEntity with error details
     */
    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<Object> handleIllegalArgumentException(IllegalArgumentException ex, WebRequest request) {
        Map<String, Object> body = new LinkedHashMap<>();
        body.put("timestamp", LocalDateTime.now());
        body.put("status", HttpStatus.BAD_REQUEST.value());
        body.put("error", "Bad Request");
        body.put("message", ex.getMessage());
        body.put("path", request.getDescription(false));
        
        return new ResponseEntity<>(body, HttpStatus.BAD_REQUEST);
    }
    
    /**
     * Handles all other exceptions and returns an INTERNAL_SERVER_ERROR response.
     *
     * @param ex the exception
     * @param request the web request
     * @return a ResponseEntity with error details
     */
    @ExceptionHandler(Exception.class)
    public ResponseEntity<Object> handleGlobalException(Exception ex, WebRequest request) {
        Map<String, Object> body = new LinkedHashMap<>();
        body.put("timestamp", LocalDateTime.now());
        body.put("status", HttpStatus.INTERNAL_SERVER_ERROR.value());
        body.put("error", "Internal Server Error");
        body.put("message", "An unexpected error occurred: " + ex.getMessage());
        body.put("path", request.getDescription(false));
        
        return new ResponseEntity<>(body, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}