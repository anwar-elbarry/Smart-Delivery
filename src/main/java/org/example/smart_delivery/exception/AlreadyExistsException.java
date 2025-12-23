package org.example.smart_delivery.exception;

public class AlreadyExistsException extends RuntimeException {
    public AlreadyExistsException(String message) {
        super(message+" is already exists");
    }
}
