package com.example.delivery.service.Exceptions;

public class FailedGiveParcels extends RuntimeException {
    public FailedGiveParcels(String message) {
        super(message);
    }

    public FailedGiveParcels(String message, Throwable cause) {
        super(message, cause);
    }
}
