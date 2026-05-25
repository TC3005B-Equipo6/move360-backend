package com.e6.application.usecase.dashboard;

import com.e6.application.dto.dashboard.GetDashboardsResponseDTO;
import com.e6.domain.repository.DashboardRepository;
import jakarta.enterprise.context.ApplicationScoped;

import java.util.List;

@ApplicationScoped
public class GetPublicDashboardsUseCase {

    private final DashboardRepository dashboardRepository;

    public GetPublicDashboardsUseCase(DashboardRepository dashboardRepository) {
        this.dashboardRepository = dashboardRepository;
    }

    public List<GetDashboardsResponseDTO> execute(){
        return dashboardRepository.getPublicDashboards();
    }
}
