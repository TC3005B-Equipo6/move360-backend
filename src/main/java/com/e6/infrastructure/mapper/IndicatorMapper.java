package com.e6.infrastructure.mapper;

import com.e6.domain.model.Indicator.Indicator;
import com.e6.infrastructure.entity.IndicatorEntity;

import java.util.Set;
import java.util.stream.Collectors;

public final class IndicatorMapper {

    public static Indicator toDomain(IndicatorEntity entity) {
        return Indicator.builder()
                .id(entity.getId())
                .title(entity.getTitle())
                //.subtitle(entity.getSubtitle())
                //.type(entity.getType())
                //.data(entity.getData())
                //.delta(entity.getDelta())
                //.deltaData(entity.getDeltaData())
                //.operation(entity.getOperation())
                .startDate(entity.getStartDate())
                .endDate(entity.getEndDate())
                .query(entity.getQuery())
                .dashboard(entity.getDashboard().getId())
                //.coordinate(entity.getCoordinate())
                //.source(entity.getSource())
                .build();
        // TODO: crear SourceMapper y mapear entity.getSource() -> indicator.setSource(...)
    }

    public static Indicator toDomainWithoutDashboard(IndicatorEntity entity) {
        return Indicator.builder()
                .id(entity.getId())
                .title(entity.getTitle())
                //.subtitle(entity.getSubtitle())
                //.type(entity.getType())
                //.data(entity.getData())
                //.delta(entity.getDelta())
                //.deltaData(entity.getDeltaData())
                //.operation(entity.getOperation())
                .startDate(entity.getStartDate())
                .endDate(entity.getEndDate())
                .query(entity.getQuery())
                .dashboard(entity.getDashboard().getId())
                //.coordinate(entity.getCoordinate())
                //.source(entity.getSource())
                .build();
        // TODO: crear SourceMapper y mapear entity.getSource() -> indicator.setSource(...)
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
        entity.setCoordinate(indicator.getCoordinate().toString());
        if (indicator.getDashboardId() != null) {
            //entity.setDashboard(DashboardMapper.toEntity(indicator.getDashboardId()));
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
