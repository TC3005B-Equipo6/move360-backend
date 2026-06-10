package com.e6.application.dto.dashboard;

import com.e6.domain.model.DashboardItemKind;
import com.e6.domain.model.DashboardLayoutItem;
import com.e6.domain.model.Indicator.Coordinate;

import java.util.List;

public record UpdateDashboardLayoutDTO(List<ItemDTO> items) {
    public List<DashboardLayoutItem> toDomainItems() {
        if (items == null) {
            throw new IllegalArgumentException("items is required");
        }
        return items.stream()
                .map(ItemDTO::toDomain)
                .toList();
    }

    public record ItemDTO(
            DashboardItemKind kind,
            Integer resourceId,
            Coordinate coordinate
    ) {
        DashboardLayoutItem toDomain() {
            if (kind == null || resourceId == null || coordinate == null) {
                throw new IllegalArgumentException("kind, resourceId and coordinate are required");
            }
            return new DashboardLayoutItem(kind, resourceId, coordinate);
        }
    }
}
