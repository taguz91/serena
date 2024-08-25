package com.taguz91.api_serena.api.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class ResourceNotFoundException extends RuntimeException {

    public ResourceNotFoundException() {
        super(defaultErrorMessage(null));
    }

    public ResourceNotFoundException(String message) {
        super(defaultErrorMessage(message));
    }

    private static String defaultErrorMessage(String message) {
        if (message == null) {
            return "Data not found exception";
        }

        return message;
    }
}
