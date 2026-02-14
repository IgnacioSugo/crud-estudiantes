package com.ignacio.crud.estudiantes.exceptions;

public class EmailYaExisteException extends RuntimeException {
    public EmailYaExisteException(String message) {
        super(message);
    }
}
