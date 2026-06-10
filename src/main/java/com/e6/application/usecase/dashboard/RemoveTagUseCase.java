package com.e6.application.usecase.dashboard;

import com.e6.application.service.DashboardAccessService;
import com.e6.domain.repository.DashboardRepository;
import jakarta.enterprise.context.ApplicationScoped;

import java.util.UUID;

@ApplicationScoped
public class RemoveTagUseCase {

    private final DashboardRepository dashboardRepository;
    private final DashboardAccessService dashboardAccessService;

    public RemoveTagUseCase(DashboardRepository dashboardRepository, DashboardAccessService dashboardAccessService) {
        this.dashboardRepository = dashboardRepository;
        this.dashboardAccessService = dashboardAccessService;
    }

    public void execute(UUID id, int tagId) {
        dashboardAccessService.requireOwner(dashboardRepository.findDashboardById(id));
        dashboardRepository.removeTag(id, tagId);
    }
}
