package com.e6.application.usecase.indicator;

import com.e6.application.dto.indicator.GetIndicatorResponseDTO;
import com.e6.application.dto.indicator.UpdateIndicatorDTO;
import com.e6.application.service.DashboardAccessService;
import com.e6.application.service.IndicatorMetricsCalculator;
import com.e6.domain.model.Dashboard;
import com.e6.domain.model.Indicator.Indicator;
import com.e6.domain.model.Indicator.IndicatorFilterSelection;
import com.e6.domain.repository.DashboardRepository;
import com.e6.domain.repository.IndicatorRepository;
import jakarta.enterprise.context.ApplicationScoped;

import java.util.Arrays;
import java.util.Objects;
import java.util.UUID;

@ApplicationScoped
public class UpdateIndicatorUseCase {

    private final IndicatorRepository indicatorRepository;
    private final DashboardRepository dashboardRepository;
    private final DashboardAccessService dashboardAccessService;
    private final IndicatorMetricsCalculator metricsCalculator;

    public UpdateIndicatorUseCase(
            IndicatorRepository indicatorRepository,
            DashboardRepository dashboardRepository,
            DashboardAccessService dashboardAccessService,
            IndicatorMetricsCalculator metricsCalculator) {
        this.indicatorRepository = indicatorRepository;
        this.dashboardRepository = dashboardRepository;
        this.dashboardAccessService = dashboardAccessService;
        this.metricsCalculator = metricsCalculator;
    }

    public GetIndicatorResponseDTO execute(int id, UpdateIndicatorDTO updateIndicatorDTO){
        Indicator existing = indicatorRepository.findIndicatorById(id);
        Dashboard currentDashboard = dashboardRepository.findDashboardById(existing.getDashboardId());
        dashboardAccessService.requireOwner(currentDashboard);

        UUID dashboardId = updateIndicatorDTO.dashboardId() == null
                ? existing.getDashboardId()
                : updateIndicatorDTO.dashboardId();
        if (!Objects.equals(existing.getDashboardId(), dashboardId)) {
            Dashboard targetDashboard = dashboardRepository.findDashboardById(dashboardId);
            dashboardAccessService.requireOwner(targetDashboard);
        }

        Indicator patched = Indicator.from(existing)
                .title(updateIndicatorDTO.title() == null ? existing.getTitle() : updateIndicatorDTO.title())
                .subtitle(updateIndicatorDTO.subtitle() == null ? existing.getSubtitle() : updateIndicatorDTO.subtitle())
                .type(updateIndicatorDTO.type() == null ? existing.getType() : updateIndicatorDTO.type())
                .relationship(updateIndicatorDTO.relationship() == null ? existing.getRelationship() : updateIndicatorDTO.relationship())
                .operation(updateIndicatorDTO.operation() == null ? existing.getOperation() : updateIndicatorDTO.operation())
                .startDate(updateIndicatorDTO.startDate() == null ? existing.getStartDate() : updateIndicatorDTO.startDate())
                .endDate(updateIndicatorDTO.endDate() == null ? existing.getEndDate() : updateIndicatorDTO.endDate())
                .dashboard(dashboardId)
                .coordinate(updateIndicatorDTO.coordinate() == null ? existing.getCoordinate() : updateIndicatorDTO.coordinate())
                .source(updateIndicatorDTO.sourceId() == null ? existing.getSourceId() : updateIndicatorDTO.sourceId())
                .table(updateIndicatorDTO.tableId() == null ? existing.getTableId() : updateIndicatorDTO.tableId())
                .column(resolveColumnId(existing, updateIndicatorDTO))
                .filters(updateIndicatorDTO.filters() == null ? existing.getFilters() : updateIndicatorDTO.filters().toDomain())
                .build();

        if (queryChanged(existing, patched)) {
            patched = metricsCalculator.withMetrics(patched);
        }

        return GetIndicatorResponseDTO.from(indicatorRepository.updateIndicator(patched));
    }

    private Integer resolveColumnId(Indicator existing, UpdateIndicatorDTO updateIndicatorDTO) {
        int sourceId = updateIndicatorDTO.sourceId() == null ? existing.getSourceId() : updateIndicatorDTO.sourceId();
        Integer columnId = updateIndicatorDTO.columnId() == null ? existing.getColumnId() : updateIndicatorDTO.columnId();
        return sourceId == 0 ? columnId : null;
    }

    private boolean queryChanged(Indicator existing, Indicator patched) {
        return existing.getType() != patched.getType()
                || existing.getSourceId() != patched.getSourceId()
                || existing.getTableId() != patched.getTableId()
                || !Objects.equals(existing.getColumnId(), patched.getColumnId())
                || existing.getOperation() != patched.getOperation()
                || !Objects.equals(existing.getStartDate(), patched.getStartDate())
                || !Objects.equals(existing.getEndDate(), patched.getEndDate())
                || filtersChanged(existing.getFilters(), patched.getFilters());
    }

    private boolean filtersChanged(IndicatorFilterSelection existing, IndicatorFilterSelection patched) {
        return !Arrays.equals(existing.ids(), patched.ids())
                || !Arrays.equals(existing.values(), patched.values());
    }
}
