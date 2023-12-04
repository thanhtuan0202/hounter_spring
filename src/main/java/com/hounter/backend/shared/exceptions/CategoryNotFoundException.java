package com.hounter.backend.shared.exceptions;

public class CategoryNotFoundException extends Exception {
    public CategoryNotFoundException() {
        super();
    }

    public CategoryNotFoundException(String message) {
        super(message);
    }
}
