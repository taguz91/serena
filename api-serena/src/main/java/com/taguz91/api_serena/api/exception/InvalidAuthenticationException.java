package com.taguz91.api_serena.api.exception;

import java.io.Serial;

public class InvalidAuthenticationException extends RuntimeException {

    @Serial
    private static final long serialVersionUID = 8805288776424877411L;

    public InvalidAuthenticationException() {
        super("Correo o contraseña incorrectos.");
    }
}
