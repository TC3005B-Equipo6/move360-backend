package com.e6.infrastructure.mapper;

import com.e6.domain.model.Indicator.Coordinate;
import com.e6.domain.model.Indicator.Indicator;
import com.e6.infrastructure.entity.IndicatorEntity;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.Set;
import java.util.stream.Collectors;

public final class IndicatorMapper {

    public static Indicator toDomain(IndicatorEntity entity) {
        try {
            ObjectMapper mapper = new ObjectMapper();

            return Indicator.builder()
                    .id(entity.getId())
                    .title(entity.getTitle())
                    .subtitle(entity.getSubtitle())
                    .type(entity.getType())
                    .data(entity.getData())
                    .relationship(entity.getRelationship())
                    .deltaData(entity.getDeltaData())
                    .operation(entity.getOperation())
                    .startDate(entity.getStartDate())
                    .endDate(entity.getEndDate())
                    .query(entity.getQuery())
                    .dashboard(entity.getDashboard().getId())
                    .coordinate(mapper.readValue(
                                    entity.getCoordinate(),
                                    Coordinate.class))
                    .source(entity.getSourceId())
                    .build();
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    public static Indicator toDomainWithoutDashboard(IndicatorEntity entity) {
        return toDomain(entity);
    }

    public static Set<Indicator> toDomainSetWithoutDashboard(Set<IndicatorEntity> entities) {
        return entities.stream()
                .map(IndicatorMapper::toDomainWithoutDashboard)
                .collect(Collectors.toSet());
    }

    public static IndicatorEntity toEntity(Indicator indicator) {

        try {
            ObjectMapper mapper = new ObjectMapper();
            IndicatorEntity entity = new IndicatorEntity();

            entity.setStartDate(indicator.getStartDate());
            entity.setEndDate(indicator.getEndDate());
            entity.setQuery(indicator.getQuery());
            entity.setTitle(indicator.getTitle());
            entity.setSubtitle(indicator.getSubtitle());
            entity.setCoordinate(mapper.writeValueAsString(indicator.getCoordinate()));
            entity.setSourceId(indicator.getSourceId());
            entity.setType(indicator.getType());
            entity.setData(indicator.getData());
            entity.setDeltaData(indicator.getDeltaData());
            entity.setRelationship(indicator.getRelationship());
            entity.setOperation(indicator.getOperation());
            return entity;
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    public static Set<IndicatorEntity> toEntitySet(Set<Indicator> indicators) {
        return indicators.stream()
                .map(IndicatorMapper::toEntity)
                .collect(Collectors.toSet());
    }
}
