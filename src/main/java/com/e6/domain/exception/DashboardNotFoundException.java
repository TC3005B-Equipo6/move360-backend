package com.e6.domain.exception;

public class DashboardNotFoundException extends RuntimeException {
    public DashboardNotFoundException() {
        super("No se encontró el dashboard");
    }
}

