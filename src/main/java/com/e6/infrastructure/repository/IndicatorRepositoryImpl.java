package com.e6.infrastructure.repository;

import com.e6.domain.exception.DashboardNotFoundException;
import com.e6.domain.exception.IndicatorNotFoundException;
import com.e6.domain.model.Indicator.Indicator;
import com.e6.domain.model.Indicator.Operation;
import com.e6.domain.model.source.FilterMetadata;
import com.e6.domain.repository.IndicatorRepository;
import com.e6.infrastructure.entity.DashboardEntity;
import com.e6.infrastructure.entity.IndicatorEntity;
import com.e6.infrastructure.mapper.IndicatorMapper;
import io.quarkus.hibernate.orm.panache.PanacheRepositoryBase;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.container.ResourceInfo;

import java.time.LocalDate;
import java.util.Set;

@ApplicationScoped
public class IndicatorRepositoryImpl implements IndicatorRepository, PanacheRepositoryBase<IndicatorEntity, Integer> {

    private final ResourceInfo resourceInfo;

    @Inject
    public IndicatorRepositoryImpl(ResourceInfo resourceInfo) {
        this.resourceInfo = resourceInfo;
    }

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
            WHERE STR_TO_DATE(CONCAT(year, '-', month, '-01'), '%%Y-%%m-%%d')
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
    @Transactional
    public Indicator createIndicator(Indicator indicator) {
        try {
            IndicatorEntity entity = IndicatorMapper.toEntity(indicator);
            DashboardEntity dashboardReference = getEntityManager().getReference(DashboardEntity.class, indicator.getDashboardId());
            entity.setDashboard(dashboardReference);
            persist(entity);
            return IndicatorMapper.toDomain(entity);
        } catch (EntityNotFoundException e) {
            throw new DashboardNotFoundException(indicator.getDashboardId().toString());
        }
    }

    @Override
    public Indicator findIndicatorById(int id) {
        return IndicatorMapper.toDomain(findByIdOptional(id)
                .orElseThrow(() -> new IndicatorNotFoundException(String.valueOf(id))));
    }

    @Override
    @Transactional
    public Indicator updateIndicator(Indicator indicator) {
        IndicatorEntity indicatorEntity = findByIdOptional(indicator.getId())
                .orElseThrow(() -> new IndicatorNotFoundException(String.valueOf(indicator.getId())));

        try {
            DashboardEntity dashboardReference = getEntityManager().getReference(DashboardEntity.class, indicator.getDashboardId());
            indicatorEntity.setDashboard(dashboardReference);
            IndicatorMapper.copyToEntity(indicator, indicatorEntity);
            flush();
            return IndicatorMapper.toDomain(indicatorEntity);
        } catch (EntityNotFoundException e) {
            throw new DashboardNotFoundException(indicator.getDashboardId().toString());
        }
    }

    @Override
    @Transactional
    public void deleteIndicatorById(int id) {
        boolean deleted = deleteById(id);
        if (!deleted)
            throw new DashboardNotFoundException(String.valueOf(id));
    }
}
