package com.e6.application.usecase.dashboard;

import com.e6.domain.repository.DashboardRepository;
import jakarta.enterprise.context.ApplicationScoped;

import java.util.UUID;

@ApplicationScoped
public class RemoveTagUseCase {

    private final DashboardRepository dashboardRepository;

    public RemoveTagUseCase(DashboardRepository dashboardRepository) {
        this.dashboardRepository = dashboardRepository;
    }

    public void execute(UUID id, int tagId) {
        dashboardRepository.removeTag(id, tagId);
    }
}
