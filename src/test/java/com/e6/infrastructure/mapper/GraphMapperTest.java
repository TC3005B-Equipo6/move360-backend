package com.e6.infrastructure.mapper;

import com.e6.domain.model.Indicator.Coordinate;
import com.e6.domain.model.graph.Graph;
import com.e6.domain.model.graph.GraphOperation;
import com.e6.domain.model.graph.GraphSize;
import com.e6.domain.model.graph.GraphType;
import com.e6.infrastructure.entity.DashboardEntity;
import com.e6.infrastructure.entity.GraphEntity;
import com.e6.infrastructure.entity.SeriesEntity;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;

class GraphMapperTest {

    @Test
    void mapsTransientSeriesWithoutGeneratedId() throws Exception {
        ObjectMapper mapper = new ObjectMapper();
        DashboardEntity dashboard = new DashboardEntity();
        dashboard.setId(UUID.randomUUID());

        SeriesEntity series = new SeriesEntity();
        series.setSeriesKey("passengers");
        series.setLabel("Pasajeros");
        series.setColor("#2563EB");
        series.setData(mapper.writeValueAsString(List.of(Map.of("name", "2026-01", "value", 100.0))));

        GraphEntity entity = new GraphEntity();
        entity.setId(12);
        entity.setDashboard(dashboard);
        entity.setTitle("Pasajeros");
        entity.setSubtitle("Mensual");
        entity.setSize(GraphSize.CHART_MD);
        entity.setType(GraphType.BAR);
        entity.setSourceId(0);
        entity.setTableId(0);
        entity.setDimensionColumn("month");
        entity.setMetricColumns(mapper.writeValueAsString(List.of("passengers")));
        entity.setOperation(GraphOperation.SUM);
        entity.setCompareEnabled(false);
        entity.setStartMonth("2026-01");
        entity.setEndMonth("2026-01");
        entity.setStartDate(LocalDate.of(2026, 1, 1));
        entity.setEndDate(LocalDate.of(2026, 1, 31));
        entity.setQuery("{}");
        entity.setCoordinate(mapper.writeValueAsString(new Coordinate(0, 0)));
        entity.setDelta(null);
        entity.setData(mapper.writeValueAsString(List.of(Map.of("name", "2026-01", "passengers", 100.0))));
        entity.getSeries().add(series);

        Graph graph = GraphMapper.toDomain(entity);

        assertEquals(0, graph.getSeries().getFirst().getId());
        assertEquals("passengers", graph.getSeries().getFirst().getSeriesKey());
    }
}
