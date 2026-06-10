package com.e6.domain.exception;

public class GraphNotFoundException extends RuntimeException {
    public GraphNotFoundException(String id) {
        super(String.format("No se encontró gráfica con el id: %s", id));
    }
}
