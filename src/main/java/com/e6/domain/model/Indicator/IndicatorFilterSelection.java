package com.e6.domain.model.Indicator;

public record IndicatorFilterSelection(
        int[] ids,
        String[] values
) {
    public IndicatorFilterSelection {
        ids = ids == null ? new int[0] : ids;
        values = values == null ? new String[0] : values;

        if (ids.length != values.length) {
            throw new IllegalArgumentException("Filter ids and values must have the same length");
        }
    }

    public static IndicatorFilterSelection empty() {
        return new IndicatorFilterSelection(new int[0], new String[0]);
    }
}
