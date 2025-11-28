package com.jobportal.backend.Exception;

import com.jobportal.backend.Exception.*;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.WebRequest;

import java.util.Date;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(EmailNotFoundException.class)
    public ResponseEntity<?> handleEmailNotFoundException(EmailNotFoundException ex, WebRequest request) {
        return buildResponse(ex.getMessage(), request, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(OtpInvalidException.class)
    public ResponseEntity<?> handleOtpInvalidException(OtpInvalidException ex, WebRequest request) {
        return buildResponse(ex.getMessage(), request, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(OtpExpiredException.class)
    public ResponseEntity<?> handleOtpExpiredException(OtpExpiredException ex, WebRequest request) {
        return buildResponse(ex.getMessage(), request, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<?> handleGlobalException(Exception ex, WebRequest request) {
        return buildResponse(ex.getMessage(), request, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    private ResponseEntity<?> buildResponse(String message, WebRequest request, HttpStatus status) {
        ErrorDetails errorDetails = new ErrorDetails(new Date(), message, request.getDescription(false));
        return new ResponseEntity<>(errorDetails, status);
    }

    public static class ErrorDetails {
        private Date timestamp;
        private String message;
        private String details;

        public ErrorDetails(Date timestamp, String message, String details) {
            this.timestamp = timestamp;
            this.message = message;
            this.details = details;
        }

        public Date getTimestamp() { return timestamp; }
        public String getMessage() { return message; }
        public String getDetails() { return details; }
    }
}
