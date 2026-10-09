package com.techlab.excepciones;

import java.io.Serial;

public abstract class NegocioException extends RuntimeException {

    @Serial
    private static final long serialVersionUID = 1L;

    protected NegocioException(String mensaje) {
        super(mensaje);
    }
}
