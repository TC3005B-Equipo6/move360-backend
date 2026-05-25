package com.e6.application.dto.dashboard;

import com.e6.domain.model.Dashboard;

import java.time.LocalDateTime;
import java.util.UUID;

public record GetUserDashboardsResponseDTO(
        UUID id,
        String title,
        LocalDateTime createdAt,
        boolean isPublic
) {
    public static GetUserDashboardsResponseDTO from(Dashboard dashboard) {
        return new GetUserDashboardsResponseDTO(
                dashboard.getId(), dashboard.getTitle(), dashboard.getCreatedAt(), dashboard.isPublic()
        );
    }
}
