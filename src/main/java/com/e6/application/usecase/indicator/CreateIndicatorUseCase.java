package com.e6.application.usecase.indicator;

import com.e6.application.dto.indicator.CreateIndicatorDTO;
import com.e6.application.dto.indicator.CreateIndicatorResponseDTO;
import com.e6.application.dto.indicator.IndicatorFiltersDTO;
import com.e6.domain.model.Indicator.Indicator;
import com.e6.domain.model.Indicator.IndicatorType;
import com.e6.domain.model.source.Metadata;
import com.e6.domain.repository.IndicatorRepository;
import com.e6.domain.repository.SourceRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.enterprise.context.ApplicationScoped;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

@ApplicationScoped
public class CreateIndicatorUseCase {

    private static final double ZERO_EPSILON = 1e-9;

    private final IndicatorRepository indicatorRepository;
    private final SourceRepository sourceRepository;

    public CreateIndicatorUseCase(IndicatorRepository indicatorRepository, SourceRepository sourceRepository) {
        this.indicatorRepository = indicatorRepository;
        this.sourceRepository = sourceRepository;
    }

    public CreateIndicatorResponseDTO execute(CreateIndicatorDTO createIndicatorDTO){
        try {
            ObjectMapper mapper = new ObjectMapper();
            IndicatorFiltersDTO filters = createIndicatorDTO.filters() == null
                    ? IndicatorFiltersDTO.empty()
                    : createIndicatorDTO.filters();

            Metadata metadata = sourceRepository.getMetadata(
                    createIndicatorDTO.sourceId(),
                    createIndicatorDTO.tableId(),
                    createIndicatorDTO.columnId(),
                    filters);

            Double rangeData = aggregate(
                    createIndicatorDTO,
                    metadata,
                    createIndicatorDTO.startDate(),
                    createIndicatorDTO.endDate());
            Double data = calculateData(createIndicatorDTO, metadata, rangeData);

            long days = ChronoUnit.DAYS.between(createIndicatorDTO.startDate(), createIndicatorDTO.endDate());
            LocalDate previousStart = createIndicatorDTO.startDate().minusDays(days);

            Double deltaData = rangeData - aggregate(
                    createIndicatorDTO,
                    metadata,
                    previousStart,
                    createIndicatorDTO.startDate());

            Indicator indicator = Indicator.builder()
                    .title(createIndicatorDTO.title())
                    .subtitle(createIndicatorDTO.subtitle())
                    .type(createIndicatorDTO.type())
                    .data(data)
                    .relationship(createIndicatorDTO.relationship())
                    .deltaData(deltaData)
                    .operation(createIndicatorDTO.operation())
                    .startDate(createIndicatorDTO.startDate())
                    .endDate(createIndicatorDTO.endDate())
                    .query(mapper.writeValueAsString(metadata))
                    .dashboard(createIndicatorDTO.dashboardId())
                    .coordinate(createIndicatorDTO.coordinate())
                    .source(createIndicatorDTO.sourceId())
                    .table(createIndicatorDTO.tableId())
                    .column(createIndicatorDTO.sourceId() == 0 ? createIndicatorDTO.columnId() : null)
                    .filters(filters.toDomain())
                    .build();

            indicator = indicatorRepository.createIndicator(indicator);

            System.out.println("indicator terminado");

            return new CreateIndicatorResponseDTO(
                    indicator.getId(),
                    indicator.getTitle(),
                    indicator.getSubtitle(),
                    indicator.getType(),
                    indicator.getRelationship(),
                    indicator.getDeltaData(),
                    indicator.getData()
            );
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    private Double calculateData(CreateIndicatorDTO createIndicatorDTO, Metadata metadata, Double rangeData) {
        if (createIndicatorDTO.type() != IndicatorType.PERCENTAGE) {
            return rangeData;
        }

        LocalDate startMonthStart = createIndicatorDTO.startDate().withDayOfMonth(1);
        LocalDate startMonthEnd = createIndicatorDTO.startDate().withDayOfMonth(createIndicatorDTO.startDate().lengthOfMonth());
        LocalDate endMonthStart = createIndicatorDTO.endDate().withDayOfMonth(1);
        LocalDate endMonthEnd = createIndicatorDTO.endDate().withDayOfMonth(createIndicatorDTO.endDate().lengthOfMonth());

        Double startValue = aggregate(createIndicatorDTO, metadata, startMonthStart, startMonthEnd);
        Double endValue = aggregate(createIndicatorDTO, metadata, endMonthStart, endMonthEnd);

        if (startValue == null || endValue == null || Math.abs(startValue) < ZERO_EPSILON) {
            return null;
        }

        return ((endValue - startValue) / startValue) * 100;
    }

    private Double aggregate(CreateIndicatorDTO createIndicatorDTO, Metadata metadata, LocalDate startDate, LocalDate endDate) {
        return indicatorRepository.aggregate(
                createIndicatorDTO.sourceId() == 1,
                metadata.tableName(),
                metadata.columnName(),
                createIndicatorDTO.operation(),
                startDate,
                endDate,
                metadata.filters()
        );
    }
}
