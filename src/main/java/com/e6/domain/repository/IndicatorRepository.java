package com.e6.domain.repository;

import com.e6.application.dto.indicator.*;
import com.e6.domain.model.Indicator.Indicator;
import com.e6.domain.model.Indicator.Operation;
import com.e6.domain.model.source.FilterMetadata;

import java.time.LocalDate;
import java.util.List;
import java.util.Set;

public interface IndicatorRepository {
    Double aggregate(boolean year, String tableName, String columnName, Operation operation, LocalDate startDate, LocalDate endDate, Set<FilterMetadata> filters);

    Indicator createIndicator(Indicator indicator);

    GetIndicatorResponseDTO findIndicatorById(int id);

    UpdateIndicatorResponseDTO updateIndicator(Indicator indicator);

    void deleteIndicatorById(int id);
}
