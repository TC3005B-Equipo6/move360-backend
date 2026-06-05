package com.e6.application.usecase.indicator;

import com.e6.application.dto.indicator.CreateIndicatorDTO;
import com.e6.application.dto.indicator.CreateIndicatorResponseDTO;
import com.e6.application.dto.indicator.IndicatorFiltersDTO;
import com.e6.application.dto.source.FilterResponseDTO;
import com.e6.application.dto.source.SourceItemResponseDTO;
import com.e6.domain.model.Indicator.Coordinate;
import com.e6.domain.model.Indicator.Indicator;
import com.e6.domain.model.Indicator.IndicatorType;
import com.e6.domain.model.Indicator.Operation;
import com.e6.domain.model.Indicator.Relationship;
import com.e6.domain.model.graph.GraphCatalog;
import com.e6.domain.model.source.FilterMetadata;
import com.e6.domain.model.source.Metadata;
import com.e6.domain.repository.IndicatorRepository;
import com.e6.domain.repository.SourceRepository;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

class CreateIndicatorUseCaseTest {

    private static final double EPSILON = 1e-9;
    private static final LocalDate START_DATE = LocalDate.of(2026, 1, 1);
    private static final LocalDate END_DATE = LocalDate.of(2026, 2, 28);

    @Test
    void percentageDataUsesDifferenceBetweenStartAndEndMonth() {
        Fixture fixture = new Fixture();
        fixture.indicatorRepository.whenAggregate(START_DATE, END_DATE, 19.0);
        fixture.indicatorRepository.whenAggregate(LocalDate.of(2026, 1, 1), LocalDate.of(2026, 1, 31), 10.0);
        fixture.indicatorRepository.whenAggregate(LocalDate.of(2026, 2, 1), LocalDate.of(2026, 2, 28), 9.0);

        CreateIndicatorResponseDTO response = fixture.useCase()
                .execute(dto(IndicatorType.PERCENTAGE, Operation.SUM));

        assertEquals(-10.0, response.data(), EPSILON);
        assertEquals(-10.0, fixture.indicatorRepository.saved.getData(), EPSILON);
        assertTrue(fixture.indicatorRepository.wasCalled(LocalDate.of(2026, 1, 1), LocalDate.of(2026, 1, 31)));
        assertTrue(fixture.indicatorRepository.wasCalled(LocalDate.of(2026, 2, 1), LocalDate.of(2026, 2, 28)));
    }

    @Test
    void percentageDataIsNullWhenStartValueIsZero() {
        Fixture fixture = new Fixture();
        fixture.indicatorRepository.whenAggregate(START_DATE, END_DATE, 9.0);
        fixture.indicatorRepository.whenAggregate(LocalDate.of(2026, 1, 1), LocalDate.of(2026, 1, 31), 0.0);
        fixture.indicatorRepository.whenAggregate(LocalDate.of(2026, 2, 1), LocalDate.of(2026, 2, 28), 9.0);

        CreateIndicatorResponseDTO response = fixture.useCase()
                .execute(dto(IndicatorType.PERCENTAGE, Operation.SUM));

        assertNull(response.data());
        assertNull(fixture.indicatorRepository.saved.getData());
    }

    @Test
    void numberDataUsesFullRangeAggregate() {
        Fixture fixture = new Fixture();
        fixture.indicatorRepository.whenAggregate(START_DATE, END_DATE, 19.0);

        CreateIndicatorResponseDTO response = fixture.useCase()
                .execute(dto(IndicatorType.NUMBER, Operation.AVG));

        assertEquals(19.0, response.data(), EPSILON);
        assertEquals(19.0, fixture.indicatorRepository.saved.getData(), EPSILON);
        assertEquals(2, fixture.indicatorRepository.calls.size());
    }

    @Test
    void percentageDataDoesNotChangeDeltaDataCalculation() {
        Fixture fixture = new Fixture();
        LocalDate previousStart = START_DATE.minusDays(ChronoUnit.DAYS.between(START_DATE, END_DATE));
        fixture.indicatorRepository.whenAggregate(START_DATE, END_DATE, 200.0);
        fixture.indicatorRepository.whenAggregate(previousStart, START_DATE, 150.0);
        fixture.indicatorRepository.whenAggregate(LocalDate.of(2026, 1, 1), LocalDate.of(2026, 1, 31), 10.0);
        fixture.indicatorRepository.whenAggregate(LocalDate.of(2026, 2, 1), LocalDate.of(2026, 2, 28), 9.0);

        CreateIndicatorResponseDTO response = fixture.useCase()
                .execute(dto(IndicatorType.PERCENTAGE, Operation.SUM));

        assertEquals(-10.0, response.data(), EPSILON);
        assertEquals(50.0, response.deltaData(), EPSILON);
        assertEquals(50.0, fixture.indicatorRepository.saved.getDeltaData(), EPSILON);
    }

    private static CreateIndicatorDTO dto(IndicatorType type, Operation operation) {
        return new CreateIndicatorDTO(
                "Pasajeros",
                "Enero a febrero",
                type,
                Relationship.DIRECT,
                operation,
                START_DATE,
                END_DATE,
                UUID.randomUUID(),
                1,
                2,
                3,
                null,
                new Coordinate(1, 2));
    }

    private static class Fixture {
        private final FakeIndicatorRepository indicatorRepository = new FakeIndicatorRepository();
        private final FakeSourceRepository sourceRepository = new FakeSourceRepository();

        CreateIndicatorUseCase useCase() {
            return new CreateIndicatorUseCase(indicatorRepository, sourceRepository);
        }
    }

    private record AggregatePeriod(LocalDate startDate, LocalDate endDate) {
    }

    private record AggregateCall(
            boolean year,
            String tableName,
            String columnName,
            Operation operation,
            LocalDate startDate,
            LocalDate endDate,
            Set<FilterMetadata> filters) {
    }

    private static class FakeIndicatorRepository implements IndicatorRepository {
        private final Map<AggregatePeriod, Double> aggregates = new HashMap<>();
        private final List<AggregateCall> calls = new ArrayList<>();
        private Indicator saved;

        void whenAggregate(LocalDate startDate, LocalDate endDate, Double value) {
            aggregates.put(new AggregatePeriod(startDate, endDate), value);
        }

        boolean wasCalled(LocalDate startDate, LocalDate endDate) {
            return calls.stream()
                    .anyMatch(call -> call.startDate().equals(startDate) && call.endDate().equals(endDate));
        }

        @Override
        public Double aggregate(boolean year, String tableName, String columnName, Operation operation, LocalDate startDate, LocalDate endDate, Set<FilterMetadata> filters) {
            calls.add(new AggregateCall(year, tableName, columnName, operation, startDate, endDate, filters));
            return aggregates.getOrDefault(new AggregatePeriod(startDate, endDate), 0.0);
        }

        @Override
        public Indicator createIndicator(Indicator indicator) {
            this.saved = indicator;
            indicator.setId(123);
            return indicator;
        }

        @Override
        public Indicator findIndicatorById(int id) {
            throw new UnsupportedOperationException();
        }

        @Override
        public CreateIndicatorResponseDTO updateIndicator(Indicator indicator) {
            throw new UnsupportedOperationException();
        }

        @Override
        public void deleteIndicatorById(int id) {
            throw new UnsupportedOperationException();
        }
    }

    private static class FakeSourceRepository implements SourceRepository {
        @Override
        public List<SourceItemResponseDTO> getSource() {
            throw new UnsupportedOperationException();
        }

        @Override
        public List<SourceItemResponseDTO> getTables(int id) {
            throw new UnsupportedOperationException();
        }

        @Override
        public List<SourceItemResponseDTO> getColumns(int id) {
            throw new UnsupportedOperationException();
        }

        @Override
        public List<FilterResponseDTO> getFilters(int id) {
            throw new UnsupportedOperationException();
        }

        @Override
        public Metadata getMetadata(int sourceId, int tableId, int columnId, IndicatorFiltersDTO filters) {
            return new Metadata("ridership", "passengers", Set.of());
        }

        @Override
        public GraphCatalog getGraphCatalog() {
            throw new UnsupportedOperationException();
        }

        @Override
        public GraphCatalog.GraphCatalogTable resolveGraphTable(int sourceId, int tableId) {
            throw new UnsupportedOperationException();
        }

        @Override
        public void requireGraphDimension(GraphCatalog.GraphCatalogTable table, String dimensionColumn) {
            throw new UnsupportedOperationException();
        }

        @Override
        public void requireGraphMetrics(GraphCatalog.GraphCatalogTable table, List<String> metricColumns) {
            throw new UnsupportedOperationException();
        }

        @Override
        public String getGraphMetricLabel(GraphCatalog.GraphCatalogTable table, String metricColumn) {
            throw new UnsupportedOperationException();
        }
    }
}
