package com.e6.application.dto.tag;

import jakarta.validation.constraints.Size;

public record UpdateTagDTO(
        @Size(min = 1, message = "El nombre no puede ser vacío")
        String name,
        int colorId
) {
}
