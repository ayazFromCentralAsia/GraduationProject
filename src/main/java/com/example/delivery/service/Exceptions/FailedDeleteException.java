package com.example.delivery.service.Exceptions;

public class FailedDeleteException extends RuntimeException {
    public FailedDeleteException(String message) {
        super(message);
    }

    public FailedDeleteException(String message, Throwable cause) {
        super(message, cause);
    }
}
