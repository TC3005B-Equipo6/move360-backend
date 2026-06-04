package com.e6.application.dto.indicator;

public record IndicatorFiltersDTO(
        int[] ids,
        String[] values
) {
}