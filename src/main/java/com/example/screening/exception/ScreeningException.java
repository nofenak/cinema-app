package com.example.screening.exception;

public abstract class ScreeningException extends RuntimeException {

    public ScreeningException(String message) {
        super(message);
    }
}