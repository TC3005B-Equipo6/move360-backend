package com.e6.application.usecase.graph;

import com.e6.application.dto.graph.CreateGraphDTO;
import com.e6.application.dto.graph.GraphResponseDTO;
import com.e6.application.service.DashboardAccessService;
import com.e6.domain.model.Dashboard;
import com.e6.domain.model.graph.Graph;
import com.e6.domain.model.graph.GraphSnapshot;
import com.e6.domain.repository.DashboardRepository;
import com.e6.domain.repository.GraphDataQuery;
import com.e6.domain.repository.GraphRepository;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class CreateGraphUseCase {

    private final GraphRepository graphRepository;
    private final DashboardRepository dashboardRepository;
    private final GraphDataQuery graphDataQuery;
    private final DashboardAccessService dashboardAccessService;

    public CreateGraphUseCase(
            GraphRepository graphRepository,
            DashboardRepository dashboardRepository,
            GraphDataQuery graphDataQuery,
            DashboardAccessService dashboardAccessService) {
        this.graphRepository = graphRepository;
        this.dashboardRepository = dashboardRepository;
        this.graphDataQuery = graphDataQuery;
        this.dashboardAccessService = dashboardAccessService;
    }

    public GraphResponseDTO execute(CreateGraphDTO createGraphDTO) {
        Dashboard dashboard = dashboardRepository.findDashboardById(createGraphDTO.dashboardId());
        dashboardAccessService.requireOwner(dashboard);

        Graph graph = Graph.builder()
                .dashboardId(createGraphDTO.dashboardId())
                .size(createGraphDTO.size())
                .type(createGraphDTO.type())
                .sourceId(createGraphDTO.sourceId())
                .tableId(createGraphDTO.tableId())
                .dimensionColumn(createGraphDTO.dimensionColumn())
                .metricColumns(createGraphDTO.metricColumns())
                .operation(createGraphDTO.operation())
                .compareEnabled(Boolean.TRUE.equals(createGraphDTO.compareEnabled()))
                .compareTableId(createGraphDTO.compareTableId())
                .startMonth(createGraphDTO.startMonth())
                .endMonth(createGraphDTO.endMonth())
                .coordinate(createGraphDTO.coordinate())
                .query("{}")
                .build();

        GraphSnapshot snapshot = graphDataQuery.calculate(graph);
        graph = Graph.from(graph)
                .delta(snapshot.delta())
                .data(snapshot.data())
                .series(snapshot.series())
                .build();

        return GraphResponseDTO.from(graphRepository.createGraph(graph));
    }
}
