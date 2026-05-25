package com.e6.application.dto.dashboard;

import com.e6.domain.model.Dashboard;

public record CreateDashboardResponseDTO(String title, String description, String user) {
    public static CreateDashboardResponseDTO from(Dashboard dashboard) {
        return new CreateDashboardResponseDTO(
                dashboard.getTitle(),
                dashboard.getDescription(),
                dashboard.getOwner().getFirstName() + dashboard.getOwner().getPaternalSurname()
        );
    }
}
