package com.e6.application.dto.color;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record CreateColorDTO(
        @NotNull(message = "El nombre es obligatorio")
        String name,
        @NotNull
        @Size(min = 7, max = 7, message = "El valor hex debe tener 7 caracteres")
        String hex) {
}
