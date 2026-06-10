package com.e6.application.usecase.graph;

import com.e6.application.service.DashboardAccessService;
import com.e6.domain.model.Dashboard;
import com.e6.domain.model.graph.Graph;
import com.e6.domain.repository.DashboardRepository;
import com.e6.domain.repository.GraphRepository;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class DeleteGraphByIdUseCase {

    private final GraphRepository graphRepository;
    private final DashboardRepository dashboardRepository;
    private final DashboardAccessService dashboardAccessService;

    public DeleteGraphByIdUseCase(
            GraphRepository graphRepository,
            DashboardRepository dashboardRepository,
            DashboardAccessService dashboardAccessService) {
        this.graphRepository = graphRepository;
        this.dashboardRepository = dashboardRepository;
        this.dashboardAccessService = dashboardAccessService;
    }

    public void execute(int id) {
        Graph graph = graphRepository.findGraphById(id);
        Dashboard dashboard = dashboardRepository.findDashboardById(graph.getDashboardId());
        dashboardAccessService.requireOwner(dashboard);
        graphRepository.deleteGraphById(id);
    }
}
