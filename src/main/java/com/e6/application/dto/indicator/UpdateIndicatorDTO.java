package com.e6.application.dto.indicator;

import com.e6.domain.model.Indicator.Coordinate;
import com.e6.domain.model.Indicator.IndicatorType;
import com.e6.domain.model.Indicator.Operation;
import com.e6.domain.model.Indicator.Relationship;

import java.time.LocalDate;
import java.util.UUID;

public record UpdateIndicatorDTO(
        String title,
        String subtitle,
        IndicatorType type,
        Double data,
        Relationship relationship,
        Double deltaData,
        Operation operation,
        LocalDate startDate,
        LocalDate endDate,
        String query,
        UUID dashboardId,
        Coordinate coordinate,
        int sourceId
) {
}