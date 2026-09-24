package com.example.productapi.exception;

public class InvalidRefreshTokenException  extends RuntimeException{
    public InvalidRefreshTokenException(String message) {
        super(message);
    }
}
