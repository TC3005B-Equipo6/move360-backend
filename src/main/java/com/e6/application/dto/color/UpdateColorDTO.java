package com.e6.application.dto.color;

import jakarta.validation.constraints.Size;

public record UpdateColorDTO(
        @Size(min = 1, message = "El nombre es obligatorio")
        String name,
        @Size(min = 7, max = 7, message = "El valor hex debe tener 7 caracteres")
        String hex) {
}
