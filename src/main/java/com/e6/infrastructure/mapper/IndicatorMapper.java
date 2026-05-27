package com.e6.infrastructure.mapper;

import com.e6.domain.model.Indicator;
import com.e6.infrastructure.entity.IndicatorEntity;

import java.util.Set;
import java.util.stream.Collectors;

public final class IndicatorMapper {

    public static Indicator toDomain(IndicatorEntity entity) {
        Indicator indicator = new Indicator();
        indicator.setId(entity.getId() == null ? 0 : entity.getId());
        indicator.setStartDate(entity.getStartDate());
        indicator.setEndDate(entity.getEndDate());
        indicator.setQuery(entity.getQuery());
        indicator.setTitle(entity.getTitle());
        indicator.setCoordinate(entity.getCoordinate());
        indicator.setDashboard(DashboardMapper.toDomainSimple(entity.getDashboard()));
        if (entity.getColor() != null) {
            indicator.setColor(ColorMapper.toDomain(entity.getColor()));
        }
        // TODO: crear SourceMapper y mapear entity.getSource() -> indicator.setSource(...)
        return indicator;
    }

    public static Indicator toDomainWithoutDashboard(IndicatorEntity entity) {
        Indicator indicator = new Indicator();
        indicator.setId(entity.getId() == null ? 0 : entity.getId());
        indicator.setStartDate(entity.getStartDate());
        indicator.setEndDate(entity.getEndDate());
        indicator.setQuery(entity.getQuery());
        indicator.setTitle(entity.getTitle());
        indicator.setCoordinate(entity.getCoordinate());
        if (entity.getColor() != null) {
            indicator.setColor(ColorMapper.toDomain(entity.getColor()));
        }
        // TODO: crear SourceMapper y mapear entity.getSource() -> indicator.setSource(...)
        return indicator;
    }

    public static Set<Indicator> toDomainSetWithoutDashboard(Set<IndicatorEntity> entities) {
        return entities.stream()
                .map(IndicatorMapper::toDomainWithoutDashboard)
                .collect(Collectors.toSet());
    }

    public static IndicatorEntity toEntity(Indicator indicator) {
        IndicatorEntity entity = new IndicatorEntity();
        if (indicator.getId() != 0) {
            entity.setId(indicator.getId());
        }
        entity.setStartDate(indicator.getStartDate());
        entity.setEndDate(indicator.getEndDate());
        entity.setQuery(indicator.getQuery());
        entity.setTitle(indicator.getTitle());
        entity.setCoordinate(indicator.getCoordinate());
        if (indicator.getDashboard() != null) {
            entity.setDashboard(DashboardMapper.toEntity(indicator.getDashboard()));
        }
        if (indicator.getColor() != null) {
            entity.setColor(ColorMapper.toEntity(indicator.getColor()));
        }
        // TODO: crear SourceMapper y setear entity.setSource(...) — FK nullable=false, persistir Indicator sin source FALLA
        return entity;
    }

    public static Set<IndicatorEntity> toEntitySet(Set<Indicator> indicators) {
        return indicators.stream()
                .map(IndicatorMapper::toEntity)
                .collect(Collectors.toSet());
    }
}
