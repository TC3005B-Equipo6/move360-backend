package com.e6.domain.repository;

import com.e6.application.dto.indicator.*;
import com.e6.domain.model.Indicator.Indicator;
import com.e6.domain.model.Indicator.Operation;

import java.time.LocalDate;

public interface IndicatorRepository {
    Double aggregate(String tableName, String columnName, Operation operation, LocalDate startDate, LocalDate endDate);
    Indicator createIndicator(Indicator indicator);
    GetIndicatorResponseDTO findIndicatorById(int id);
    UpdateIndicatorResponseDTO updateIndicator(Indicator indicator);
    void deleteIndicatorById(int id);
}
