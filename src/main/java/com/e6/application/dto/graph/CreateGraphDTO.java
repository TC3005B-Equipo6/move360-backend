package com.e6.application.dto.graph;

import com.e6.domain.model.Indicator.Coordinate;
import com.e6.domain.model.graph.GraphOperation;
import com.e6.domain.model.graph.GraphSize;
import com.e6.domain.model.graph.GraphType;
import jakarta.validation.constraints.NotNull;

import java.util.List;
import java.util.UUID;

public record CreateGraphDTO(
        @NotNull UUID dashboardId,
        @NotNull GraphSize size,
        @NotNull GraphType type,
        @NotNull Integer sourceId,
        @NotNull Integer tableId,
        @NotNull String dimensionColumn,
        @NotNull List<String> metricColumns,
        @NotNull GraphOperation operation,
        Boolean compareEnabled,
        Integer compareTableId,
        @NotNull String startMonth,
        @NotNull String endMonth,
        @NotNull Coordinate coordinate
) {
}
