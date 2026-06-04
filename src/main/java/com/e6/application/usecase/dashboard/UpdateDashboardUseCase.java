package com.e6.application.usecase.dashboard;

import com.e6.application.dto.dashboard.CreateDashboardResponseDTO;
import com.e6.application.dto.dashboard.UpdateDashboardDTO;
import com.e6.application.service.DashboardAccessService;
import com.e6.domain.model.Dashboard;
import com.e6.domain.repository.DashboardRepository;
import jakarta.enterprise.context.ApplicationScoped;

import java.util.UUID;

@ApplicationScoped
public class UpdateDashboardUseCase {

    private final DashboardRepository dashboardRepository;
    private final DashboardAccessService dashboardAccessService;

    public UpdateDashboardUseCase(DashboardRepository dashboardRepository, DashboardAccessService dashboardAccessService) {
        this.dashboardRepository = dashboardRepository;
        this.dashboardAccessService = dashboardAccessService;
    }

    public CreateDashboardResponseDTO execute(UUID id, UpdateDashboardDTO updateDashboardDTO){
        dashboardAccessService.requireOwner(dashboardRepository.findDashboardById(id));
        Dashboard dashboard = Dashboard.builder()
                .id(id)
                .title(updateDashboardDTO.title())
                .description(updateDashboardDTO.description())
                .build();
        return dashboardRepository.updateDashboard(dashboard);

    }}
