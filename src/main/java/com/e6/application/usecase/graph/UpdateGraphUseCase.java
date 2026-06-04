package com.e6.application.usecase.graph;

import com.e6.application.dto.graph.GraphResponseDTO;
import com.e6.application.dto.graph.UpdateGraphDTO;
import com.e6.application.service.DashboardAccessService;
import com.e6.domain.model.Dashboard;
import com.e6.domain.model.graph.Graph;
import com.e6.domain.model.graph.GraphSnapshot;
import com.e6.domain.repository.DashboardRepository;
import com.e6.domain.repository.GraphDataQuery;
import com.e6.domain.repository.GraphRepository;
import jakarta.enterprise.context.ApplicationScoped;

import java.util.Objects;

@ApplicationScoped
public class UpdateGraphUseCase {

    private final GraphRepository graphRepository;
    private final DashboardRepository dashboardRepository;
    private final GraphDataQuery graphDataQuery;
    private final DashboardAccessService dashboardAccessService;

    public UpdateGraphUseCase(
            GraphRepository graphRepository,
            DashboardRepository dashboardRepository,
            GraphDataQuery graphDataQuery,
            DashboardAccessService dashboardAccessService) {
        this.graphRepository = graphRepository;
        this.dashboardRepository = dashboardRepository;
        this.graphDataQuery = graphDataQuery;
        this.dashboardAccessService = dashboardAccessService;
    }

    public GraphResponseDTO execute(int id, UpdateGraphDTO updateGraphDTO) {
        Graph existing = graphRepository.findGraphById(id);
        Dashboard dashboard = dashboardRepository.findDashboardById(existing.getDashboardId());
        dashboardAccessService.requireOwner(dashboard);

        Graph patched = Graph.from(existing)
                .size(updateGraphDTO.size() == null ? existing.getSize() : updateGraphDTO.size())
                .type(updateGraphDTO.type() == null ? existing.getType() : updateGraphDTO.type())
                .sourceId(updateGraphDTO.sourceId() == null ? existing.getSourceId() : updateGraphDTO.sourceId())
                .tableId(updateGraphDTO.tableId() == null ? existing.getTableId() : updateGraphDTO.tableId())
                .dimensionColumn(updateGraphDTO.dimensionColumn() == null ? existing.getDimensionColumn() : updateGraphDTO.dimensionColumn())
                .metricColumns(updateGraphDTO.metricColumns() == null ? existing.getMetricColumns() : updateGraphDTO.metricColumns())
                .operation(updateGraphDTO.operation() == null ? existing.getOperation() : updateGraphDTO.operation())
                .compareEnabled(updateGraphDTO.compareEnabled() == null ? existing.isCompareEnabled() : updateGraphDTO.compareEnabled())
                .compareTableId(resolveCompareTableId(existing, updateGraphDTO))
                .startMonth(updateGraphDTO.startMonth() == null ? existing.getStartMonth() : updateGraphDTO.startMonth())
                .endMonth(updateGraphDTO.endMonth() == null ? existing.getEndMonth() : updateGraphDTO.endMonth())
                .coordinate(updateGraphDTO.coordinate() == null ? existing.getCoordinate() : updateGraphDTO.coordinate())
                .build();

        if (queryChanged(existing, patched)) {
            GraphSnapshot snapshot = graphDataQuery.calculate(patched);
            patched = Graph.from(patched)
                    .delta(snapshot.delta())
                    .data(snapshot.data())
                    .series(snapshot.series())
                    .build();
        }

        return GraphResponseDTO.from(graphRepository.updateGraph(patched));
    }

    private Integer resolveCompareTableId(Graph existing, UpdateGraphDTO updateGraphDTO) {
        if (Boolean.FALSE.equals(updateGraphDTO.compareEnabled())) {
            return null;
        }
        return updateGraphDTO.compareTableId() == null ? existing.getCompareTableId() : updateGraphDTO.compareTableId();
    }

    private boolean queryChanged(Graph existing, Graph patched) {
        return existing.getType() != patched.getType()
                || existing.getSourceId() != patched.getSourceId()
                || existing.getTableId() != patched.getTableId()
                || !Objects.equals(existing.getDimensionColumn(), patched.getDimensionColumn())
                || !Objects.equals(existing.getMetricColumns(), patched.getMetricColumns())
                || existing.getOperation() != patched.getOperation()
                || existing.isCompareEnabled() != patched.isCompareEnabled()
                || !Objects.equals(existing.getCompareTableId(), patched.getCompareTableId())
                || !Objects.equals(existing.getStartMonth(), patched.getStartMonth())
                || !Objects.equals(existing.getEndMonth(), patched.getEndMonth());
    }
}
