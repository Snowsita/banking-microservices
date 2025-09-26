package com.etorres.banking.clients.exception;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ProblemDetail;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.ArrayList;
import java.util.List;

@RestControllerAdvice
public class GlobalExceptionHandler {

    private static final Logger log = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ProblemDetail handleMethodArgumentNotValidException(MethodArgumentNotValidException ex) {
        log.error("ERROR: Error de validación en la solicitud", ex);
        ProblemDetail problemDetail = ProblemDetail.forStatus(400);
        problemDetail.setTitle("Error de validación");
        problemDetail.setDetail("La solicitud contiene datos no válidos.");
        List<String> errors = new ArrayList<>();
        ex.getBindingResult().getFieldErrors().forEach(error -> errors.add(error.getDefaultMessage()));
        problemDetail.setProperty("errors", errors);
        log.error("ERROR: Detalles de la validación: {}", errors);
        return problemDetail;
    }
}
