package com.e6.application.dto.color;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record UpdateColorDTO(
        @NotNull
        int id,
        @NotBlank
        String name,
        @NotBlank
        String hex) {
}
