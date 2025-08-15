package com.mithpath.backend.exception;

import com.mithpath.backend.exception.response.ErrorResponse;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.LocalDateTime;

@ControllerAdvice
@RequiredArgsConstructor
public class GlobalExceptionHandler {
    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    public ResponseEntity<ErrorResponse> handleMethodNotSupported(HttpServletRequest req, Exception ex) {
        HttpStatus status = HttpStatus.METHOD_NOT_ALLOWED;
        return buildResponse(status, ex.getMessage(), req.getRequestURI());
    }

    @ExceptionHandler(ConnectionFailedException.class)
    public ResponseEntity<ErrorResponse> handleConnectionFailedException(HttpServletRequest req, Exception ex) {
        HttpStatus status = HttpStatus.INTERNAL_SERVER_ERROR;
        return buildResponse(status, ex.getMessage(), req.getRequestURI());
    }

    @ExceptionHandler(BadCredentialsException.class)
    public ResponseEntity<ErrorResponse> handleBadCredentials(BadCredentialsException ex) {
        HttpStatus status = HttpStatus.UNAUTHORIZED;
        return buildResponse(status, ex.getMessage(), null);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse> handleValidationException(
            HttpServletRequest req,
            MethodArgumentNotValidException ex) {

        HttpStatus status = HttpStatus.BAD_REQUEST;

        String message = "Validation failed";
        if (!ex.getBindingResult().getFieldErrors().isEmpty()) {
            message = ex.getBindingResult().getFieldErrors().get(0).getDefaultMessage();
        }

        return buildResponse(status, message, req.getRequestURI());
    }

    @ExceptionHandler(EntityNotFoundException.class)
    public  ResponseEntity<ErrorResponse> handlerEntityNotFoundException(HttpServletRequest req, Exception ex){
        HttpStatus status = HttpStatus.NOT_FOUND;
        return buildResponse(status, ex.getMessage(), req.getRequestURI());
    }

    @ExceptionHandler(DuplicateException.class)
    public  ResponseEntity<ErrorResponse> handlerDuplicateException(HttpServletRequest req, Exception ex){
        HttpStatus status = HttpStatus.CONFLICT;
        return buildResponse(status, ex.getMessage(), req.getRequestURI());
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<ErrorResponse> handleIllegalArgumentException(HttpServletRequest req, IllegalArgumentException ex) {
        HttpStatus status = HttpStatus.BAD_REQUEST;
        return buildResponse(status, ex.getMessage(), req.getRequestURI());
    }


    private ResponseEntity<ErrorResponse> buildResponse(HttpStatus status, String message, String path) {
        ErrorResponse errorResponse = ErrorResponse.builder()
                .timestamp(LocalDateTime.now())
                .status(status.value())
                .error(status.getReasonPhrase())
                .message(message)
                .path(path)
                .build();
        return ResponseEntity.status(status).body(errorResponse);
    }
}
