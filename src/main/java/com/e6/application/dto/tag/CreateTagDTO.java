package com.e6.application.dto.tag;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record CreateTagDTO(
        @NotBlank(message = "El nombre es obligatorio")
        String name,
        @NotNull(message = "El color es obligatorio")
        int colorId
) {
}
