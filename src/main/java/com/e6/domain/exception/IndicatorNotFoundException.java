package com.e6.domain.exception;

public class IndicatorNotFoundException extends RuntimeException {
    public IndicatorNotFoundException(String id) {
        super(String.format("No se encontró indicator con el id: %s", id));
    }
}
