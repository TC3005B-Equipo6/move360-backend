package com.e6.application.dto.dashboard;

import jakarta.validation.constraints.NotBlank;

public record CreateDashboardDTO(
        @NotBlank(message = "El título es obligatorio")
        String title,
        String description,
        boolean isPublic) {}
