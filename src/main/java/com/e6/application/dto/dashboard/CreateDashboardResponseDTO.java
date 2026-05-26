package com.e6.application.dto.dashboard;

import com.e6.domain.model.Dashboard;

import java.util.UUID;

public record CreateDashboardResponseDTO(UUID id, String title, String description, String user) {
    public static CreateDashboardResponseDTO from(Dashboard dashboard) {
        return new CreateDashboardResponseDTO(
                dashboard.getId(),
                dashboard.getTitle(),
                dashboard.getDescription(),
                dashboard.getOwner().getFirstName() + dashboard.getOwner().getPaternalSurname()
        );
    }
}
