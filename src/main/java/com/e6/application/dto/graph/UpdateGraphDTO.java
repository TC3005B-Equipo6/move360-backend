package com.e6.application.dto.graph;

import com.e6.domain.model.Indicator.Coordinate;
import com.e6.domain.model.graph.GraphOperation;
import com.e6.domain.model.graph.GraphSize;
import com.e6.domain.model.graph.GraphType;

import java.util.List;

public record UpdateGraphDTO(
        GraphSize size,
        GraphType type,
        Integer sourceId,
        Integer tableId,
        String dimensionColumn,
        List<String> metricColumns,
        GraphOperation operation,
        Boolean compareEnabled,
        Integer compareTableId,
        String startMonth,
        String endMonth,
        Coordinate coordinate
) {
}
