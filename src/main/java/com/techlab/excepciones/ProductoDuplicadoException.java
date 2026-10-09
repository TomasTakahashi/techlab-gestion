package com.techlab.excepciones;

import java.io.Serial;

public class ProductoDuplicadoException extends NegocioException {

    @Serial
    private static final long serialVersionUID = 1L;

    public ProductoDuplicadoException(String nombre) {
        super("El producto '" + nombre + "' ya existe");
    }

}
