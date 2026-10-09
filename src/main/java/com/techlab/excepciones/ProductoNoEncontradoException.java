package com.techlab.excepciones;

import java.io.Serial;

public class ProductoNoEncontradoException extends NegocioException {

    @Serial
    private static final long serialVersionUID = 1L;

    public ProductoNoEncontradoException(Long id) {
        super("Producto con ID " + id + " no encontrado");
    }

}
