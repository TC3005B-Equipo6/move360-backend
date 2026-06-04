package com.e6.infrastructure.repository;

import com.e6.application.dto.indicator.CreateIndicatorResponseDTO;
import com.e6.application.dto.indicator.GetIndicatorResponseDTO;
import com.e6.application.dto.indicator.UpdateIndicatorResponseDTO;
import com.e6.domain.model.Indicator.Indicator;
import com.e6.domain.model.Indicator.Operation;
import com.e6.domain.repository.IndicatorRepository;
import com.e6.infrastructure.entity.IndicatorEntity;
import com.e6.infrastructure.mapper.IndicatorMapper;
import io.quarkus.hibernate.orm.panache.PanacheRepositoryBase;
import jakarta.enterprise.context.ApplicationScoped;

import java.time.LocalDate;

@ApplicationScoped
public class IndicatorRepositoryImpl implements IndicatorRepository, PanacheRepositoryBase<IndicatorEntity, Integer> {

    @Override
    public Double aggregate(String tableName, String columnName, Operation operation, LocalDate startDate, LocalDate endDate) {
        String sql = """
                SELECT COALESCE(%s(%s),0)
                FROM %s
                WHERE make_date(year, month, day)
                      BETWEEN :startDate AND :endDater
                """
                .formatted(
                        operation.toString(),
                        columnName,
                        tableName
                );
        Object result = getEntityManager()
                .createNativeQuery(sql)
                .setParameter("startDate", startDate)
                .setParameter("endDate", endDate)
                .getSingleResult();
        return result == null ? 0.0 : ((Number) result).doubleValue();
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
