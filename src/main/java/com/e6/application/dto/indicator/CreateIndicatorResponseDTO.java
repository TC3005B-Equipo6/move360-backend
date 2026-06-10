package com.e6.application.dto.indicator;

import com.e6.domain.model.Indicator.Relationship;
import com.e6.domain.model.Indicator.IndicatorType;

public record CreateIndicatorResponseDTO(
        int id,
        String title,
        String subtitle,
        IndicatorType type,
        Relationship relationship,
        Double deltaData,
        Double data
) {
}
