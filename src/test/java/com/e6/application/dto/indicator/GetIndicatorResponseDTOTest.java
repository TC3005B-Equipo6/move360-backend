package com.e6.application.dto.indicator;

import com.e6.domain.model.Indicator.Coordinate;
import com.e6.domain.model.Indicator.Indicator;
import com.e6.domain.model.Indicator.IndicatorFilterSelection;
import com.e6.domain.model.Indicator.IndicatorType;
import com.e6.domain.model.Indicator.Operation;
import com.e6.domain.model.Indicator.Relationship;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

class GetIndicatorResponseDTOTest {

    @Test
    void mapsIndicatorOriginForEditModal() {
        UUID dashboardId = UUID.randomUUID();
        Indicator indicator = Indicator.builder()
                .id(9)
                .dashboard(dashboardId)
                .title("Pasajeros")
                .subtitle("Linea 1")
                .type(IndicatorType.NUMBER)
                .data(120.0)
                .relationship(Relationship.DIRECT)
                .deltaData(12.0)
                .operation(Operation.SUM)
                .startDate(LocalDate.of(2026, 1, 1))
                .endDate(LocalDate.of(2026, 1, 31))
                .coordinate(new Coordinate(2, 3))
                .source(1)
                .table(5)
                .column(null)
                .filters(new IndicatorFilterSelection(new int[] { 2, 4 }, new String[] { "A", "B" }))
                .build();

        GetIndicatorResponseDTO response = GetIndicatorResponseDTO.from(indicator);

        assertEquals("indicator:9", response.itemId());
        assertEquals(dashboardId, response.dashboardId());
        assertEquals(1, response.sourceId());
        assertEquals(5, response.tableId());
        assertNull(response.columnId());
        assertArrayEquals(new int[] { 2, 4 }, response.filters().ids());
        assertArrayEquals(new String[] { "A", "B" }, response.filters().values());
    }
}
