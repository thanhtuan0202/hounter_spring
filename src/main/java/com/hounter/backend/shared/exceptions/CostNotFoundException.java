package com.hounter.backend.shared.exceptions;

public class CostNotFoundException extends RuntimeException {
    public CostNotFoundException() {
        super();
    }

    public CostNotFoundException(String message) {
        super(message);
    }
}
