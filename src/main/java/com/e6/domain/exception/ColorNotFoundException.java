package com.e6.domain.exception;

public class ColorNotFoundException extends RuntimeException{
    public ColorNotFoundException(String id) {
        super(String.format("No se encontró Color con el id: %s", id));
    }
}
