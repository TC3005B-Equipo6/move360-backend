package com.e6.application.usecase.dashboard;

import com.e6.domain.model.Dashboard;
import com.e6.domain.repository.DashboardRepository;
import jakarta.enterprise.context.ApplicationScoped;

import java.util.UUID;

@ApplicationScoped
public class GetDashboardByIdUseCase {

    private final DashboardRepository dashboardRepository;

    public GetDashboardByIdUseCase(DashboardRepository dashboardRepository) {
        this.dashboardRepository = dashboardRepository;
    }

    public Dashboard execute(UUID id){
        return dashboardRepository.findDashboardById(id);
    }
}
