package com.ignacio.crud.estudiantes.exceptions;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.ignacio.crud.estudiantes.dto.ErrorResponseDTO;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@ControllerAdvice
public class GlobalExceptionHandler {
    
    @ExceptionHandler(EstudianteNoEncontradoException.class)
    public ResponseEntity<ErrorResponseDTO> handleEstudianteNoEncontrado(
        EstudianteNoEncontradoException ex) {

            log.warn("Estudiante no encontrado: ", ex);

            ErrorResponseDTO error = new ErrorResponseDTO(
                HttpStatus.NOT_FOUND.value(),
                ex.getMessage(),
                LocalDateTime.now()
            );
            
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);
    }

    @ExceptionHandler(EmailYaExisteException.class)
    public ResponseEntity<ErrorResponseDTO> handleEmailYaExiste(
        EmailYaExisteException ex) {
            ErrorResponseDTO error = new ErrorResponseDTO(
                HttpStatus.CONFLICT.value(),
                ex.getMessage(),
                LocalDateTime.now()
            );
            log.warn("Email ya existe: {}", ex.getMessage());
            return ResponseEntity.status(HttpStatus.CONFLICT).body(error);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponseDTO> handleValidationErrors(
        MethodArgumentNotValidException ex) {

            Map<String, String> errors = new HashMap<>();
            ex.getBindingResult().getFieldErrors()
                .forEach(err -> errors.put(err.getField(), err.getDefaultMessage()));
            

            ErrorResponseDTO error = new ErrorResponseDTO(
                HttpStatus.BAD_REQUEST.value(),
                "Error de validación",
                LocalDateTime.now()
            );
            log.warn("Error de validación {}: ", ex.getMessage());

            return ResponseEntity.badRequest().body(error);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponseDTO> handleGeneric(Exception ex) {
        
        ErrorResponseDTO error = new ErrorResponseDTO(
            HttpStatus.INTERNAL_SERVER_ERROR.value(),
            "Error interno del servidor",
            LocalDateTime.now()
        );
        log.error("Error interno del servidor: ", ex);
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(error);
    }

}
 