package com.e6.application.dto.graph;

import com.e6.domain.model.Indicator.Coordinate;
import com.e6.domain.model.graph.Graph;
import com.e6.domain.model.graph.GraphOperation;
import com.e6.domain.model.graph.GraphSeries;
import com.e6.domain.model.graph.GraphSize;
import com.e6.domain.model.graph.GraphType;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Map;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;

class GraphResponseDTOTest {

    @Test
    void mapsGraphDomainToFlatResponseContract() {
        UUID dashboardId = UUID.randomUUID();
        Graph graph = Graph.builder()
                .id(12)
                .dashboardId(dashboardId)
                .title("Pasajeros por mes")
                .subtitle("Línea 1")
                .size(GraphSize.CHART_MD)
                .type(GraphType.BAR)
                .sourceId(0)
                .tableId(1)
                .dimensionColumn("month")
                .metricColumns(List.of("passengers"))
                .operation(GraphOperation.SUM)
                .compareEnabled(true)
                .compareTableId(2)
                .startMonth("2026-01")
                .endMonth("2026-03")
                .coordinate(new Coordinate(3, 4))
                .delta(12.5)
                .data(List.of(Map.of("name", "2026-01", "passengers", 100.0)))
                .series(List.of(new GraphSeries(
                        7,
                        "passengers",
                        "Pasajeros",
                        "#2563EB",
                        List.of(Map.of("name", "2026-01", "value", 100.0)))))
                .build();

        GraphResponseDTO response = GraphResponseDTO.from(graph);

        assertEquals("graph:12", response.itemId());
        assertEquals(12, response.id());
        assertEquals(dashboardId, response.dashboardId());
        assertEquals("Pasajeros por mes", response.title());
        assertEquals("Línea 1", response.subtitle());
        assertEquals(GraphSize.CHART_MD, response.size());
        assertEquals(GraphType.BAR, response.type());
        assertEquals(List.of("passengers"), response.metricColumns());
        assertEquals(new Coordinate(3, 4), response.coordinate());
        assertEquals(12.5, response.delta());
        assertEquals("passengers", response.series().getFirst().seriesKey());
    }
}
