package com.e6.application.service;

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
public class IndicatorMetricsCalculator {

    private static final double ZERO_EPSILON = 1e-9;

    private final IndicatorRepository indicatorRepository;
    private final SourceRepository sourceRepository;
    private final ObjectMapper mapper = new ObjectMapper();

    public IndicatorMetricsCalculator(IndicatorRepository indicatorRepository, SourceRepository sourceRepository) {
        this.indicatorRepository = indicatorRepository;
        this.sourceRepository = sourceRepository;
    }

    public Indicator withMetrics(Indicator indicator) {
        try {
            IndicatorFiltersDTO filters = IndicatorFiltersDTO.from(indicator.getFilters());
            int columnId = metadataColumnId(indicator);
            Metadata metadata = sourceRepository.getMetadata(
                    indicator.getSourceId(),
                    indicator.getTableId(),
                    columnId,
                    filters);

            Double rangeData = aggregate(indicator, metadata, indicator.getStartDate(), indicator.getEndDate());
            Double data = calculateData(indicator, metadata, rangeData);

            long days = ChronoUnit.DAYS.between(indicator.getStartDate(), indicator.getEndDate());
            LocalDate previousStart = indicator.getStartDate().minusDays(days);
            Double deltaData = rangeData - aggregate(indicator, metadata, previousStart, indicator.getStartDate());

            return Indicator.from(indicator)
                    .data(data)
                    .deltaData(deltaData)
                    .query(mapper.writeValueAsString(metadata))
                    .column(storedColumnId(indicator))
                    .build();
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    private Double calculateData(Indicator indicator, Metadata metadata, Double rangeData) {
        if (indicator.getType() != IndicatorType.PERCENTAGE) {
            return rangeData;
        }

        LocalDate startMonthStart = indicator.getStartDate().withDayOfMonth(1);
        LocalDate startMonthEnd = indicator.getStartDate().withDayOfMonth(indicator.getStartDate().lengthOfMonth());
        LocalDate endMonthStart = indicator.getEndDate().withDayOfMonth(1);
        LocalDate endMonthEnd = indicator.getEndDate().withDayOfMonth(indicator.getEndDate().lengthOfMonth());

        Double startValue = aggregate(indicator, metadata, startMonthStart, startMonthEnd);
        Double endValue = aggregate(indicator, metadata, endMonthStart, endMonthEnd);

        if (startValue == null || endValue == null || Math.abs(startValue) < ZERO_EPSILON) {
            return null;
        }

        return ((endValue - startValue) / startValue) * 100;
    }

    private Double aggregate(Indicator indicator, Metadata metadata, LocalDate startDate, LocalDate endDate) {
        return indicatorRepository.aggregate(
                indicator.getSourceId() == 1,
                metadata.tableName(),
                metadata.columnName(),
                indicator.getOperation(),
                startDate,
                endDate,
                metadata.filters()
        );
    }

    private int metadataColumnId(Indicator indicator) {
        if (indicator.getSourceId() == 0) {
            if (indicator.getColumnId() == null) {
                throw new IllegalArgumentException("columnId is required for sourceId 0");
            }
            return indicator.getColumnId();
        }
        return indicator.getColumnId() == null ? 0 : indicator.getColumnId();
    }

    private Integer storedColumnId(Indicator indicator) {
        return indicator.getSourceId() == 0 ? indicator.getColumnId() : null;
    }
}
