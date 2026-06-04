package com.e6.application.usecase.dashboard;

import com.e6.application.dto.dashboard.DashboardDetailResponseDTO;
import com.e6.application.dto.dashboard.UpdateDashboardLayoutDTO;
import com.e6.application.service.DashboardAccessService;
import com.e6.domain.model.Dashboard;
import com.e6.domain.repository.DashboardRepository;
import jakarta.enterprise.context.ApplicationScoped;

import java.util.UUID;

@ApplicationScoped
public class UpdateDashboardLayoutUseCase {

    private final DashboardRepository dashboardRepository;
    private final DashboardAccessService dashboardAccessService;

    public UpdateDashboardLayoutUseCase(DashboardRepository dashboardRepository, DashboardAccessService dashboardAccessService) {
        this.dashboardRepository = dashboardRepository;
        this.dashboardAccessService = dashboardAccessService;
    }

    public DashboardDetailResponseDTO execute(UUID id, UpdateDashboardLayoutDTO updateDashboardLayoutDTO) {
        if (updateDashboardLayoutDTO == null) {
            throw new IllegalArgumentException("layout body is required");
        }
        Dashboard dashboard = dashboardRepository.findDashboardById(id);
        dashboardAccessService.requireOwner(dashboard);
        return DashboardDetailResponseDTO.from(dashboardRepository.updateLayout(id, updateDashboardLayoutDTO.toDomainItems()));
    }
}
