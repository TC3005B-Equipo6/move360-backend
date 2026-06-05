package com.e6.application.usecase.indicator;

import com.e6.application.dto.indicator.CreateIndicatorDTO;
import com.e6.application.dto.indicator.CreateIndicatorResponseDTO;
import com.e6.application.dto.indicator.IndicatorFiltersDTO;
import com.e6.application.service.IndicatorMetricsCalculator;
import com.e6.domain.model.Indicator.Indicator;
import com.e6.domain.repository.IndicatorRepository;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class CreateIndicatorUseCase {

    private final IndicatorRepository indicatorRepository;
    private final IndicatorMetricsCalculator metricsCalculator;

    public CreateIndicatorUseCase(IndicatorRepository indicatorRepository, IndicatorMetricsCalculator metricsCalculator) {
        this.indicatorRepository = indicatorRepository;
        this.metricsCalculator = metricsCalculator;
    }

    public CreateIndicatorResponseDTO execute(CreateIndicatorDTO createIndicatorDTO){
        IndicatorFiltersDTO filters = createIndicatorDTO.filters() == null
                ? IndicatorFiltersDTO.empty()
                : createIndicatorDTO.filters();

        Indicator indicator = Indicator.builder()
                .title(createIndicatorDTO.title())
                .subtitle(createIndicatorDTO.subtitle())
                .type(createIndicatorDTO.type())
                .relationship(createIndicatorDTO.relationship())
                .operation(createIndicatorDTO.operation())
                .startDate(createIndicatorDTO.startDate())
                .endDate(createIndicatorDTO.endDate())
                .dashboard(createIndicatorDTO.dashboardId())
                .coordinate(createIndicatorDTO.coordinate())
                .source(createIndicatorDTO.sourceId())
                .table(createIndicatorDTO.tableId())
                .column(createIndicatorDTO.columnId())
                .filters(filters.toDomain())
                .build();

        indicator = indicatorRepository.createIndicator(metricsCalculator.withMetrics(indicator));

        return new CreateIndicatorResponseDTO(
                indicator.getId(),
                indicator.getTitle(),
                indicator.getSubtitle(),
                indicator.getType(),
                indicator.getRelationship(),
                indicator.getDeltaData(),
                indicator.getData()
        );
    }
}
