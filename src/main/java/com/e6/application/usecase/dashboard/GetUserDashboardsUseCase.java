package com.e6.application.usecase.dashboard;

import com.e6.domain.model.Dashboard;
import com.e6.domain.repository.DashboardRepository;
import com.e6.infrastructure.security.AuthContext;
import jakarta.enterprise.context.ApplicationScoped;

import java.util.List;

@ApplicationScoped
public class GetUserDashboardsUseCase {

    private final AuthContext authContext;
    private final DashboardRepository dashboardRepository;

    public GetUserDashboardsUseCase(AuthContext authContext, DashboardRepository dashboardRepository) {
        this.authContext = authContext;
        this.dashboardRepository = dashboardRepository;
    }

    public List<Dashboard> execute(){
        return dashboardRepository.getUserDashboards(authContext.getUser());
    }
}
