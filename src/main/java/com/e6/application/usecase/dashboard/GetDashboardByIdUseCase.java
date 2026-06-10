package com.e6.application.usecase.dashboard;

import com.e6.application.dto.dashboard.DashboardDetailResponseDTO;
import com.e6.application.service.DashboardAccessService;
import com.e6.domain.model.Dashboard;
import com.e6.domain.repository.DashboardRepository;
import jakarta.enterprise.context.ApplicationScoped;

import java.util.UUID;

@ApplicationScoped
public class GetDashboardByIdUseCase {

    private final DashboardRepository dashboardRepository;
    private final DashboardAccessService dashboardAccessService;

    public GetDashboardByIdUseCase(DashboardRepository dashboardRepository, DashboardAccessService dashboardAccessService) {
        this.dashboardRepository = dashboardRepository;
        this.dashboardAccessService = dashboardAccessService;
    }

    public DashboardDetailResponseDTO execute(UUID id) {
        Dashboard dashboard = dashboardRepository.findDashboardById(id);
        dashboardAccessService.requireReadable(dashboard);
        return DashboardDetailResponseDTO.from(dashboard);
    }
}
