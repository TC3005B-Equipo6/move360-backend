package com.e6.domain.exception;

public class TagNotFoundException extends RuntimeException {
    public TagNotFoundException(String id) {
        super(String.format("No se encontró Tag con el id: %s", id));
    }
}
