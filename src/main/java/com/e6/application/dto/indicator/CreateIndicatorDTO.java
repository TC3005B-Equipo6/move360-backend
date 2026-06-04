package com.e6.application.dto.indicator;

import com.e6.domain.model.Indicator.Coordinate;
import com.e6.domain.model.Indicator.Relationship;
import com.e6.domain.model.Indicator.IndicatorType;
import com.e6.domain.model.Indicator.Operation;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;
import java.util.UUID;

public record CreateIndicatorDTO(
    @NotNull(message = "El título es obligatorio")
    String title,
    @NotNull(message = "El subtítulo es obligatorio")
    String subtitle,
    @NotNull(message = "El tipo de indicator es obligatorio")
    IndicatorType type,
    @NotNull(message = "El tipo de relación es obligatorio")
    Relationship relationship,
    @NotNull(message = "La operación es obligatorio")
    Operation operation,
    @NotNull(message = "La fecha de inicio es obligatoria")
    LocalDate startDate,
    @NotNull(message = "La fecha de fin es obligatoria")
    LocalDate endDate,
    @NotNull(message = "El id del dashboard es obligatorio")
    UUID dashboardId,
    @NotNull(message = "La fuente es obligatoria")
    int sourceId,
    @NotNull(message = "La tabla es obligatoria")
    int tableId,
    @NotNull(message = "La columna es obligatoria")
    int columnId,
    @NotNull(message = "La coordenada es obligatoria")
    Coordinate coordinate
) {
}
