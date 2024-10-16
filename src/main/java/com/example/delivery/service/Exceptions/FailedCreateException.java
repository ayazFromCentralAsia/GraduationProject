package com.example.delivery.service.Exceptions;

public class FailedCreateException extends RuntimeException{
    public FailedCreateException(String message) {
        super(message);
    }

    public FailedCreateException(String message, Throwable cause) {
        super(message, cause);
    }
}
