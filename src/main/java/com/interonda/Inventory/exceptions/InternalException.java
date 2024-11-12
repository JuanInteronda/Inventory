package com.interonda.Inventory.exceptions;

import java.time.LocalDateTime;

public class InternalException extends RuntimeException {
    private final String userMessage;
    private final LocalDateTime timestamp;

    public InternalException() {
        this("Ocurrió un error interno en el servidor.");
    }

    public InternalException(String userMessage) {
        super(userMessage);
        this.userMessage = userMessage;
        this.timestamp = LocalDateTime.now();
    }

    public String getUserMessage() {
        return userMessage;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }
}