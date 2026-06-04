package com.e6.application.dto.indicator;

import com.e6.domain.model.Indicator.IndicatorFilterSelection;

public record IndicatorFiltersDTO(
        int[] ids,
        String[] values
) {
    public IndicatorFiltersDTO {
        ids = ids == null ? new int[0] : ids;
        values = values == null ? new String[0] : values;

        if (ids.length != values.length) {
            throw new IllegalArgumentException("Filter ids and values must have the same length");
        }
    }

    public static IndicatorFiltersDTO empty() {
        return new IndicatorFiltersDTO(new int[0], new String[0]);
    }

    public static IndicatorFiltersDTO from(IndicatorFilterSelection filters) {
        IndicatorFilterSelection safeFilters = filters == null ? IndicatorFilterSelection.empty() : filters;
        return new IndicatorFiltersDTO(safeFilters.ids(), safeFilters.values());
    }

    public IndicatorFilterSelection toDomain() {
        return new IndicatorFilterSelection(ids, values);
    }
}
