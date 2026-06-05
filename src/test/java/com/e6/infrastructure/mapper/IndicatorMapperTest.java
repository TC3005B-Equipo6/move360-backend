package com.e6.infrastructure.mapper;

import com.e6.domain.model.Indicator.Coordinate;
import com.e6.domain.model.Indicator.Indicator;
import com.e6.domain.model.Indicator.IndicatorFilterSelection;
import com.e6.domain.model.Indicator.IndicatorType;
import com.e6.domain.model.Indicator.Operation;
import com.e6.domain.model.Indicator.Relationship;
import com.e6.infrastructure.entity.DashboardEntity;
import com.e6.infrastructure.entity.IndicatorEntity;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.persistence.Column;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Field;
import java.time.LocalDate;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

class IndicatorMapperTest {

    @Test
    void mapsIndicatorOriginToEntity() {
        Indicator indicator = Indicator.builder()
                .title("Pasajeros")
                .subtitle("Linea 1")
                .type(IndicatorType.NUMBER)
                .data(100.0)
                .relationship(Relationship.DIRECT)
                .deltaData(10.0)
                .operation(Operation.SUM)
                .startDate(LocalDate.of(2026, 1, 1))
                .endDate(LocalDate.of(2026, 1, 31))
                .query("{}")
                .coordinate(new Coordinate(1, 2))
                .source(0)
                .table(3)
                .column(4)
                .filters(new IndicatorFilterSelection(new int[] { 7 }, new String[] { "Metro" }))
                .build();

        IndicatorEntity entity = IndicatorMapper.toEntity(indicator);

        assertEquals(3, entity.getTableId());
        assertEquals(4, entity.getColumnId());
        assertEquals("{\"ids\":[7],\"values\":[\"Metro\"]}", entity.getFilters());
    }

    @Test
    void mapsNullIndicatorDataToEntity() {
        Indicator indicator = Indicator.builder()
                .title("Cambio porcentual")
                .subtitle("Base cero")
                .type(IndicatorType.PERCENTAGE)
                .data(null)
                .relationship(Relationship.DIRECT)
                .deltaData(0.0)
                .operation(Operation.SUM)
                .startDate(LocalDate.of(2026, 1, 1))
                .endDate(LocalDate.of(2026, 1, 31))
                .query("{}")
                .coordinate(new Coordinate(1, 2))
                .source(0)
                .table(3)
                .column(4)
                .filters(IndicatorFilterSelection.empty())
                .build();

        IndicatorEntity entity = IndicatorMapper.toEntity(indicator);

        assertNull(entity.getData());
    }

    @Test
    void dataColumnAllowsNull() throws Exception {
        Field dataField = IndicatorEntity.class.getDeclaredField("data");
        Column column = dataField.getAnnotation(Column.class);

        assertTrue(column.nullable());
    }

    @Test
    void mapsIndicatorOriginToDomain() throws Exception {
        UUID dashboardId = UUID.randomUUID();
        ObjectMapper mapper = new ObjectMapper();
        DashboardEntity dashboard = new DashboardEntity();
        dashboard.setId(dashboardId);

        IndicatorEntity entity = new IndicatorEntity();
        entity.setId(8);
        entity.setDashboard(dashboard);
        entity.setTitle("Pasajeros");
        entity.setSubtitle("Linea 1");
        entity.setType(IndicatorType.NUMBER);
        entity.setData(100.0);
        entity.setRelationship(Relationship.DIRECT);
        entity.setDeltaData(10.0);
        entity.setOperation(Operation.SUM);
        entity.setStartDate(LocalDate.of(2026, 1, 1));
        entity.setEndDate(LocalDate.of(2026, 1, 31));
        entity.setQuery("{}");
        entity.setCoordinate(mapper.writeValueAsString(new Coordinate(1, 2)));
        entity.setSourceId(1);
        entity.setTableId(5);
        entity.setColumnId(null);
        entity.setFilters("{\"ids\":[2],\"values\":[\"Centro\"]}");

        Indicator indicator = IndicatorMapper.toDomain(entity);

        assertEquals(dashboardId, indicator.getDashboardId());
        assertEquals(1, indicator.getSourceId());
        assertEquals(5, indicator.getTableId());
        assertNull(indicator.getColumnId());
        assertArrayEquals(new int[] { 2 }, indicator.getFilters().ids());
        assertArrayEquals(new String[] { "Centro" }, indicator.getFilters().values());
    }

    @Test
    void copyToEntityUpdatesEveryEditableField() {
        IndicatorEntity entity = new IndicatorEntity();
        entity.setTitle("Anterior");
        entity.setSubtitle("Anterior");
        entity.setType(IndicatorType.NUMBER);
        entity.setData(1.0);
        entity.setRelationship(Relationship.DIRECT);
        entity.setDeltaData(2.0);
        entity.setOperation(Operation.SUM);
        entity.setStartDate(LocalDate.of(2026, 1, 1));
        entity.setEndDate(LocalDate.of(2026, 1, 31));
        entity.setQuery("{}");
        entity.setCoordinate("{\"x\":1,\"y\":2}");
        entity.setSourceId(1);
        entity.setTableId(2);
        entity.setColumnId(null);
        entity.setFilters("{\"ids\":[],\"values\":[]}");

        Indicator indicator = Indicator.builder()
                .id(8)
                .title("Nuevo")
                .subtitle("Actualizado")
                .type(IndicatorType.PERCENTAGE)
                .data(null)
                .relationship(Relationship.INVERSE)
                .deltaData(15.0)
                .operation(Operation.AVG)
                .startDate(LocalDate.of(2026, 2, 1))
                .endDate(LocalDate.of(2026, 2, 28))
                .query("{\"tableName\":\"ridership\"}")
                .coordinate(new Coordinate(3, 4))
                .source(0)
                .table(5)
                .column(6)
                .filters(new IndicatorFilterSelection(new int[] { 9 }, new String[] { "Linea 9" }))
                .build();

        IndicatorMapper.copyToEntity(indicator, entity);

        assertEquals(8, entity.getId());
        assertEquals("Nuevo", entity.getTitle());
        assertEquals("Actualizado", entity.getSubtitle());
        assertEquals(IndicatorType.PERCENTAGE, entity.getType());
        assertNull(entity.getData());
        assertEquals(Relationship.INVERSE, entity.getRelationship());
        assertEquals(15.0, entity.getDeltaData());
        assertEquals(Operation.AVG, entity.getOperation());
        assertEquals(LocalDate.of(2026, 2, 1), entity.getStartDate());
        assertEquals(LocalDate.of(2026, 2, 28), entity.getEndDate());
        assertEquals("{\"tableName\":\"ridership\"}", entity.getQuery());
        assertEquals("{\"x\":3,\"y\":4}", entity.getCoordinate());
        assertEquals(0, entity.getSourceId());
        assertEquals(5, entity.getTableId());
        assertEquals(6, entity.getColumnId());
        assertEquals("{\"ids\":[9],\"values\":[\"Linea 9\"]}", entity.getFilters());
    }
}
