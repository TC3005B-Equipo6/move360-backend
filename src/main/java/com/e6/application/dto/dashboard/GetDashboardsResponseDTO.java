package com.e6.application.dto.dashboard;

import com.e6.domain.model.Dashboard;

import java.time.LocalDateTime;
import java.util.UUID;

public record GetDashboardsResponseDTO(
        UUID id,
        String ownerName,
        String title,
        String description,
        LocalDateTime createdDate
) {
    public static GetDashboardsResponseDTO from(Dashboard dashboard) {
        return new GetDashboardsResponseDTO(
                dashboard.getId(),
                dashboard.getOwner().getFirstName() + " " + dashboard.getOwner().getPaternalSurname(),
                dashboard.getTitle(),
                dashboard.getDescription(),
                dashboard.getCreatedAt()
        );
    }
}
