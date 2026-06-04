package com.e6.infrastructure.mapper;

import com.e6.domain.model.Indicator.Coordinate;
import com.e6.domain.model.graph.Graph;
import com.e6.domain.model.graph.GraphSeries;
import com.e6.infrastructure.entity.DashboardEntity;
import com.e6.infrastructure.entity.GraphEntity;
import com.e6.infrastructure.entity.SeriesEntity;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.time.YearMonth;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

public final class GraphMapper {

    private static final ObjectMapper MAPPER = new ObjectMapper();
    private static final TypeReference<List<String>> STRING_LIST = new TypeReference<>() {};
    private static final TypeReference<List<Map<String, Object>>> DATA_LIST = new TypeReference<>() {};

    private GraphMapper() {
    }

    public static Graph toDomain(GraphEntity entity) {
        return Graph.builder()
                .id(entity.getId())
                .dashboardId(entity.getDashboard().getId())
                .title(entity.getTitle())
                .subtitle(entity.getSubtitle())
                .size(entity.getSize())
                .type(entity.getType())
                .sourceId(entity.getSourceId())
                .tableId(entity.getTableId())
                .dimensionColumn(entity.getDimensionColumn())
                .metricColumns(readMetricColumns(entity.getMetricColumns()))
                .operation(entity.getOperation())
                .compareEnabled(entity.isCompareEnabled())
                .compareTableId(entity.getCompareTableId())
                .startMonth(entity.getStartMonth())
                .endMonth(entity.getEndMonth())
                .coordinate(readCoordinate(entity.getCoordinate()))
                .delta(entity.getDelta())
                .data(readData(entity.getData()))
                .series(toDomainSeries(entity.getSeries()))
                .query(entity.getQuery())
                .build();
    }

    public static Graph toDomainWithoutDashboard(GraphEntity entity) {
        return toDomain(entity);
    }

    public static Set<Graph> toDomainSetWithoutDashboard(Set<GraphEntity> entities) {
        return entities.stream()
                .map(GraphMapper::toDomainWithoutDashboard)
                .collect(Collectors.toSet());
    }

    public static GraphEntity toEntity(Graph graph) {
        GraphEntity entity = new GraphEntity();
        copyToEntity(graph, entity);
        if (graph.getDashboardId() != null) {
            DashboardEntity dashboard = new DashboardEntity();
            dashboard.setId(graph.getDashboardId());
            entity.setDashboard(dashboard);
        }
        return entity;
    }

    public static void copyToEntity(Graph graph, GraphEntity entity) {
        if (graph.getId() != 0) {
            entity.setId(graph.getId());
        }
        entity.setTitle(graph.getTitle());
        entity.setSubtitle(graph.getSubtitle());
        entity.setSize(graph.getSize());
        entity.setType(graph.getType());
        entity.setSourceId(graph.getSourceId());
        entity.setTableId(graph.getTableId());
        entity.setDimensionColumn(graph.getDimensionColumn());
        entity.setMetricColumns(write(graph.getMetricColumns()));
        entity.setOperation(graph.getOperation());
        entity.setCompareEnabled(graph.isCompareEnabled());
        entity.setCompareTableId(graph.getCompareTableId());
        entity.setStartMonth(graph.getStartMonth());
        entity.setEndMonth(graph.getEndMonth());
        entity.setStartDate(YearMonth.parse(graph.getStartMonth()).atDay(1));
        entity.setEndDate(YearMonth.parse(graph.getEndMonth()).atEndOfMonth());
        entity.setQuery(graph.getQuery() == null ? "{}" : graph.getQuery());
        entity.setCoordinate(write(graph.getCoordinate()));
        entity.setDelta(graph.getDelta());
        entity.setData(write(graph.getData()));

        entity.getSeries().clear();
        for (GraphSeries series : graph.getSeries()) {
            SeriesEntity seriesEntity = toEntity(series);
            seriesEntity.setGraph(entity);
            entity.getSeries().add(seriesEntity);
        }
    }

    public static Set<GraphEntity> toEntitySet(Set<Graph> graphs) {
        return graphs.stream()
                .map(GraphMapper::toEntity)
                .collect(Collectors.toSet());
    }

    private static List<GraphSeries> toDomainSeries(List<SeriesEntity> entities) {
        if (entities == null) {
            return new ArrayList<>();
        }
        return entities.stream()
                .map(entity -> new GraphSeries(
                        entity.getId(),
                        entity.getSeriesKey(),
                        entity.getLabel(),
                        entity.getColor(),
                        readData(entity.getData())))
                .toList();
    }

    private static SeriesEntity toEntity(GraphSeries series) {
        SeriesEntity entity = new SeriesEntity();
        if (series.getId() != 0) {
            entity.setId(series.getId());
        }
        entity.setSeriesKey(series.getSeriesKey());
        entity.setLabel(series.getLabel());
        entity.setColor(series.getColor());
        entity.setData(write(series.getData()));
        return entity;
    }

    private static Coordinate readCoordinate(String value) {
        try {
            return MAPPER.readValue(value, Coordinate.class);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    private static List<String> readMetricColumns(String value) {
        try {
            return MAPPER.readValue(value, STRING_LIST);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    private static List<Map<String, Object>> readData(String value) {
        try {
            return MAPPER.readValue(value, DATA_LIST);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    private static String write(Object value) {
        try {
            return MAPPER.writeValueAsString(value);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }
}
