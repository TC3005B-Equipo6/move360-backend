package com.e6.application.dto.indicator;

import com.e6.domain.model.Indicator.Coordinate;
import com.e6.domain.model.Indicator.Indicator;
import com.e6.domain.model.Indicator.IndicatorType;
import com.e6.domain.model.Indicator.Operation;
import com.e6.domain.model.Indicator.Relationship;

import java.time.LocalDate;
import java.util.UUID;

public record GetIndicatorResponseDTO(
        String itemId,
        int id,
        UUID dashboardId,
        String title,
        String subtitle,
        IndicatorType type,
        Double data,
        Relationship relationship,
        Double deltaData,
        Operation operation,
        LocalDate startDate,
        LocalDate endDate,
        Coordinate coordinate,
        int sourceId,
        int tableId,
        Integer columnId,
        IndicatorFiltersDTO filters
) {
    public static GetIndicatorResponseDTO from(Indicator indicator) {
        return new GetIndicatorResponseDTO(
                "indicator:" + indicator.getId(),
                indicator.getId(),
                indicator.getDashboardId(),
                indicator.getTitle(),
                indicator.getSubtitle(),
                indicator.getType(),
                indicator.getData(),
                indicator.getRelationship(),
                indicator.getDeltaData(),
                indicator.getOperation(),
                indicator.getStartDate(),
                indicator.getEndDate(),
                indicator.getCoordinate(),
                indicator.getSourceId(),
                indicator.getTableId(),
                indicator.getColumnId(),
                IndicatorFiltersDTO.from(indicator.getFilters())
        );
    }
}
