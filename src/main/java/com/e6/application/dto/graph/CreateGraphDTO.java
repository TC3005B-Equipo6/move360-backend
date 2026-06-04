package com.e6.application.dto.graph;

import com.e6.domain.model.Indicator.Coordinate;
import com.e6.domain.model.source.Source;
import jakarta.validation.constraints.NotBlank;

import java.time.LocalDate;

public record CreateGraphDTO(
        @NotBlank(message = "")
        LocalDate starDate,
        @NotBlank(message = "")
        LocalDate endDate,
        @NotBlank(message = "")
        Coordinate coordinate,
        @NotBlank(message = "")
        Source source,
        @NotBlank(message = "")
        String query
) {
}
