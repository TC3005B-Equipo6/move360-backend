package com.e6.domain.exception;

public class DashboardNotFoundException extends RuntimeException {
    public DashboardNotFoundException(String id) {
        super(String.format("No se encontró el dashboard con el id: %s", id));
    }
}

