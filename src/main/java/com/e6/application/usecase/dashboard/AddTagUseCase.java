package com.e6.application.usecase.dashboard;

import com.e6.application.dto.dashboard.AddTagResponseDTO;
import com.e6.domain.repository.DashboardRepository;
import jakarta.enterprise.context.ApplicationScoped;

import java.util.UUID;

@ApplicationScoped
public class AddTagUseCase {

    private final DashboardRepository dashboardRepository;

    public AddTagUseCase(DashboardRepository dashboardRepository) {
        this.dashboardRepository = dashboardRepository;
    }

    public AddTagResponseDTO execute(UUID id, int tagId) {
        return dashboardRepository.addTag(id, tagId);
    }
}
