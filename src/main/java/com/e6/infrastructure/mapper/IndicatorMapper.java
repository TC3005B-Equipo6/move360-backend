package com.e6.infrastructure.mapper;

import com.e6.domain.model.Indicator.Coordinate;
import com.e6.domain.model.Indicator.IndicatorFilterSelection;
import com.e6.domain.model.Indicator.Indicator;
import com.e6.infrastructure.entity.IndicatorEntity;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.Set;
import java.util.stream.Collectors;

public final class IndicatorMapper {

    private static final ObjectMapper MAPPER = new ObjectMapper();

    private IndicatorMapper() {
    }

    public static Indicator toDomain(IndicatorEntity entity) {
        try {
            IndicatorFilterSelection filters = entity.getFilters() == null
                    ? IndicatorFilterSelection.empty()
                    : MAPPER.readValue(entity.getFilters(), IndicatorFilterSelection.class);

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
                    .coordinate(MAPPER.readValue(
                                    entity.getCoordinate(),
                                    Coordinate.class))
                    .source(entity.getSourceId())
                    .table(entity.getTableId())
                    .column(entity.getColumnId())
                    .filters(filters)
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
        IndicatorEntity entity = new IndicatorEntity();
        copyToEntity(indicator, entity);
        return entity;
    }

    public static void copyToEntity(Indicator indicator, IndicatorEntity entity) {
        try {
            if (indicator.getId() != 0) {
                entity.setId(indicator.getId());
            }
            entity.setStartDate(indicator.getStartDate());
            entity.setEndDate(indicator.getEndDate());
            entity.setQuery(indicator.getQuery());
            entity.setTitle(indicator.getTitle());
            entity.setSubtitle(indicator.getSubtitle());
            entity.setCoordinate(MAPPER.writeValueAsString(indicator.getCoordinate()));
            entity.setSourceId(indicator.getSourceId());
            entity.setTableId(indicator.getTableId());
            entity.setColumnId(indicator.getColumnId());
            entity.setFilters(MAPPER.writeValueAsString(indicator.getFilters()));
            entity.setType(indicator.getType());
            entity.setData(indicator.getData());
            entity.setDeltaData(indicator.getDeltaData());
            entity.setRelationship(indicator.getRelationship());
            entity.setOperation(indicator.getOperation());
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
