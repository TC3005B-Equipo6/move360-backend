package com.e6.application.usecase.dashboard;

import com.e6.application.dto.dashboard.CreateDashboardDTO;
import com.e6.application.dto.dashboard.CreateDashboardResponseDTO;
import com.e6.domain.model.Dashboard;
import com.e6.domain.repository.DashboardRepository;
import com.e6.infrastructure.security.AuthContext;
import jakarta.enterprise.context.ApplicationScoped;

import java.time.LocalDateTime;
import java.util.UUID;

@ApplicationScoped
public class CreateDashboardUseCase {

    private final AuthContext authContext;
    private final DashboardRepository dashboardRepository;

    public CreateDashboardUseCase(AuthContext authContext, DashboardRepository dashboardRepository) {
        this.authContext = authContext;
        this.dashboardRepository = dashboardRepository;
    }

    public CreateDashboardResponseDTO execute(CreateDashboardDTO createDashboardDTO) {
        Dashboard dashboard = Dashboard.builder()
                .id(UUID.randomUUID())
                .owner(authContext.getUser())
                .title(createDashboardDTO.title())
                .description(createDashboardDTO.description())
                .isPublic(createDashboardDTO.isPublic())
                .createdAt(LocalDateTime.now())
                .build();

        return dashboardRepository.createDashboard(dashboard);
    }
}
