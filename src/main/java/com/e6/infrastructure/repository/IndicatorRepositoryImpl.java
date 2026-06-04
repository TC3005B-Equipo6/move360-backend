package com.e6.infrastructure.repository;

import com.e6.application.dto.indicator.GetIndicatorResponseDTO;
import com.e6.application.dto.indicator.UpdateIndicatorResponseDTO;
import com.e6.domain.model.Indicator.Indicator;
import com.e6.domain.model.Indicator.Operation;
import com.e6.domain.model.source.FilterMetadata;
import com.e6.domain.repository.IndicatorRepository;
import com.e6.infrastructure.entity.IndicatorEntity;
import com.e6.infrastructure.mapper.IndicatorMapper;
import io.quarkus.hibernate.orm.panache.PanacheRepositoryBase;
import jakarta.enterprise.context.ApplicationScoped;

import java.time.LocalDate;
import java.util.List;
import java.util.Set;

@ApplicationScoped
public class IndicatorRepositoryImpl implements IndicatorRepository, PanacheRepositoryBase<IndicatorEntity, Integer> {

    @Override
    public Double aggregate(
            boolean year,
            String tableName,
            String columnName,
            Operation operation,
            LocalDate startDate,
            LocalDate endDate,
            Set<FilterMetadata> filters) {

        StringBuilder sql = new StringBuilder();

        String baseQuery;

        if (year) {
            baseQuery = """
            SELECT COALESCE(%s(%s), 0)
            FROM %s
            WHERE DATE(CONCAT(year, '-', month, '-', day))
                  BETWEEN :startDate AND :endDate
            """
                    .formatted(
                            operation,
                            columnName,
                            tableName
                    );
        } else {
            baseQuery = """
            SELECT COALESCE(%s(%s), 0)
            FROM %s
            WHERE (year * 100 + month)
                  BETWEEN :startDate AND :endDate
            """
                    .formatted(
                            operation,
                            columnName,
                            tableName
                    );
        }

        sql.append(baseQuery);

        for (FilterMetadata filter : filters) {
            sql.append(" AND ")
                    .append(filter.columnName())
                    .append(" IN (:")
                    .append(filter.columnName())
                    .append(")");
        }

        var query = getEntityManager().createNativeQuery(sql.toString());

        query.setParameter("startDate", startDate);
        query.setParameter("endDate", endDate);

        for (FilterMetadata filter : filters) {
            query.setParameter(
                    filter.columnName(),
                    filter.value()
            );
        }

        Object result = query.getSingleResult();

        return result == null
                ? 0.0
                : ((Number) result).doubleValue();
    }

    @Override
    public Indicator createIndicator(Indicator indicator) {
        IndicatorEntity entity = IndicatorMapper.toEntity(indicator);
        persist(entity);
        return IndicatorMapper.toDomain(entity);
    }

    @Override
    public GetIndicatorResponseDTO findIndicatorById(int id) {
        return null;
    }

    @Override
    public UpdateIndicatorResponseDTO updateIndicator(Indicator indicator) {
        return null;
    }

    @Override
    public void deleteIndicatorById(int id) {

    }
}
