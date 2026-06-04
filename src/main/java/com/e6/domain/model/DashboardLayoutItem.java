package com.e6.domain.model;

import com.e6.domain.model.Indicator.Coordinate;

public record DashboardLayoutItem(
        DashboardItemKind kind,
        int resourceId,
        Coordinate coordinate
) {
}
