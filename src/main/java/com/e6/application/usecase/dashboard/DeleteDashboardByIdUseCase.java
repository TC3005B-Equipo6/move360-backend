package com.e6.application.usecase.dashboard;

import com.e6.domain.exception.DashboardNotFoundException;
import com.e6.domain.repository.DashboardRepository;
import jakarta.enterprise.context.ApplicationScoped;

import java.util.UUID;

@ApplicationScoped
public class DeleteDashboardByIdUseCase {

    private final DashboardRepository dashboardRepository;

    public DeleteDashboardByIdUseCase(DashboardRepository dashboardRepository) {
        this.dashboardRepository = dashboardRepository;
    }

    public void execute(UUID id){
        dashboardRepository.deleteDashboardById(id);
    }
}
