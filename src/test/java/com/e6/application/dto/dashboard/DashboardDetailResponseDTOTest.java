package com.e6.application.dto.dashboard;

import com.e6.domain.model.Dashboard;
import com.e6.domain.model.DashboardItemKind;
import com.e6.domain.model.Role;
import com.e6.domain.model.User;
import com.e6.domain.model.Indicator.Coordinate;
import com.e6.domain.model.Indicator.Indicator;
import com.e6.domain.model.Indicator.IndicatorType;
import com.e6.domain.model.Indicator.Operation;
import com.e6.domain.model.Indicator.Relationship;
import com.e6.domain.model.graph.Graph;
import com.e6.domain.model.graph.GraphOperation;
import com.e6.domain.model.graph.GraphSize;
import com.e6.domain.model.graph.GraphType;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Set;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class DashboardDetailResponseDTOTest {

    @Test
    void mapsIndicatorsAndGraphsIntoDashboardItems() {
        UUID dashboardId = UUID.randomUUID();
        User owner = new User(UUID.randomUUID(), "Ana", "Cruz", "", new Role(), "ana@example.com", true, "firebase");

        Indicator indicator = Indicator.builder()
                .id(2)
                .title("Pasajeros")
                .subtitle("Mensual")
                .type(IndicatorType.NUMBER)
                .relationship(Relationship.DIRECT)
                .operation(Operation.SUM)
                .data(10.0)
                .deltaData(1.0)
                .startDate(LocalDate.of(2026, 1, 1))
                .endDate(LocalDate.of(2026, 1, 31))
                .coordinate(new Coordinate(1, 2))
                .source(0)
                .dashboard(dashboardId)
                .build();

        Graph graph = Graph.builder()
                .id(5)
                .dashboardId(dashboardId)
                .size(GraphSize.CHART_SM)
                .type(GraphType.LINE)
                .sourceId(0)
                .tableId(0)
                .dimensionColumn("month")
                .metricColumns(java.util.List.of("passengers"))
                .operation(GraphOperation.SUM)
                .startMonth("2026-01")
                .endMonth("2026-02")
                .coordinate(new Coordinate(3, 4))
                .build();

        Dashboard dashboard = Dashboard.builder()
                .id(dashboardId)
                .owner(owner)
                .title("Movilidad")
                .description("Detalle")
                .createdAt(LocalDateTime.of(2026, 6, 4, 8, 0))
                .isPublic(true)
                .indicators(Set.of(indicator))
                .graphs(Set.of(graph))
                .build();

        DashboardDetailResponseDTO response = DashboardDetailResponseDTO.from(dashboard);

        assertEquals(dashboardId, response.id());
        assertEquals(2, response.items().size());
        assertTrue(response.items().stream().anyMatch(item ->
                item.kind() == DashboardItemKind.INDICATOR && item.itemId().equals("indicator:2")));
        assertTrue(response.items().stream().anyMatch(item ->
                item.kind() == DashboardItemKind.GRAPH && item.itemId().equals("graph:5")));
    }
}
