package com.example.delivery.service.Exceptions;

import lombok.Getter;

@Getter
public class NotFoundException extends RuntimeException{
    public NotFoundException(String message) {
        super(message);
    }

    public NotFoundException(String message, Throwable cause) {
        super(message, cause);
    }
}