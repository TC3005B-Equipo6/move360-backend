package com.e6.application.usecase.dashboard;

import com.e6.application.dto.dashboard.CreateDashboardDTO;
import com.e6.domain.model.Dashboard;
import com.e6.domain.repository.DashboardRepository;
import com.e6.infrastructure.security.AuthContext;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class CreateDashboardUseCase {

    private final AuthContext authContext;
    private final DashboardRepository dashboardRepository;

    public CreateDashboardUseCase(AuthContext authContext, DashboardRepository dashboardRepository) {
        this.authContext = authContext;
        this.dashboardRepository = dashboardRepository;
    }

    public Dashboard execute(CreateDashboardDTO createDashboardDTO) {
        Dashboard dashboard = new Dashboard();

        dashboard.setOwner(authContext.getUser());
        dashboard.setTitle(createDashboardDTO.title());
        dashboard.setDescription(createDashboardDTO.description());
        dashboard.setPublic(createDashboardDTO.isPublic());

        return dashboardRepository.createDashboard(dashboard);
    }
}
