package com.e6.application.usecase.dashboard;

import com.e6.application.dto.dashboard.AddTagResponseDTO;
import com.e6.application.service.DashboardAccessService;
import com.e6.domain.repository.DashboardRepository;
import jakarta.enterprise.context.ApplicationScoped;

import java.util.UUID;

@ApplicationScoped
public class AddTagUseCase {

    private final DashboardRepository dashboardRepository;
    private final DashboardAccessService dashboardAccessService;

    public AddTagUseCase(DashboardRepository dashboardRepository, DashboardAccessService dashboardAccessService) {
        this.dashboardRepository = dashboardRepository;
        this.dashboardAccessService = dashboardAccessService;
    }

    public AddTagResponseDTO execute(UUID id, int tagId) {
        dashboardAccessService.requireOwner(dashboardRepository.findDashboardById(id));
        return dashboardRepository.addTag(id, tagId);
    }
}
