package com.e6.application.dto.indicator;

import com.e6.domain.model.Indicator.Delta;
import com.e6.domain.model.Indicator.IndicatorType;

public record CreateIndicatorResponseDTO(
        int id,
        String title,
        String subtitle,
        IndicatorType type,
        Delta delta,
        Double deltaData,
        Double data
) {
}
