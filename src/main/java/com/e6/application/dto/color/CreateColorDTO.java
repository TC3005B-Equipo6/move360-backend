package com.e6.application.dto.color;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record CreateColorDTO(
        @NotBlank(message = "El nombre no puede ser vacío")
        @NotNull(message = "El nombre es obligatorio")
        String name,
        @NotNull
        @Size(min = 7, max = 7)
        String hex) {
}
