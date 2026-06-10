package com.e6.application.usecase.indicator;

import com.e6.application.dto.dashboard.AddTagResponseDTO;
import com.e6.application.dto.dashboard.CreateDashboardResponseDTO;
import com.e6.application.dto.dashboard.GetDashboardsResponseDTO;
import com.e6.application.dto.dashboard.GetUserDashboardsResponseDTO;
import com.e6.application.dto.indicator.IndicatorFiltersDTO;
import com.e6.application.dto.indicator.UpdateIndicatorDTO;
import com.e6.application.dto.source.FilterResponseDTO;
import com.e6.application.dto.source.SourceItemResponseDTO;
import com.e6.application.service.DashboardAccessService;
import com.e6.application.service.IndicatorMetricsCalculator;
import com.e6.domain.model.Dashboard;
import com.e6.domain.model.DashboardLayoutItem;
import com.e6.domain.model.Indicator.Coordinate;
import com.e6.domain.model.Indicator.Indicator;
import com.e6.domain.model.Indicator.IndicatorFilterSelection;
import com.e6.domain.model.Indicator.IndicatorType;
import com.e6.domain.model.Indicator.Operation;
import com.e6.domain.model.Indicator.Relationship;
import com.e6.domain.model.User;
import com.e6.domain.model.graph.GraphCatalog;
import com.e6.domain.model.source.FilterMetadata;
import com.e6.domain.model.source.Metadata;
import com.e6.domain.repository.DashboardRepository;
import com.e6.domain.repository.IndicatorRepository;
import com.e6.domain.repository.SourceRepository;
import com.e6.infrastructure.security.AuthContext;
import jakarta.ws.rs.ForbiddenException;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class UpdateIndicatorUseCaseTest {

    private static final double EPSILON = 1e-9;
    private static final LocalDate START_DATE = LocalDate.of(2026, 1, 1);
    private static final LocalDate END_DATE = LocalDate.of(2026, 2, 28);

    @Test
    void coordinateOnlyUpdateSkipsMetricRecompute() {
        Fixture fixture = new Fixture();

        fixture.useCase().execute(1, new UpdateIndicatorDTO(
                null,
                null,
                null,
                null,
                null,
                null,
                null,
                null,
                new Coordinate(9, 9),
                null,
                null,
                null,
                null));

        assertEquals(0, fixture.indicatorRepository.calls.size());
        assertEquals(new Coordinate(9, 9), fixture.indicatorRepository.saved.getCoordinate());
        assertEquals(100.0, fixture.indicatorRepository.saved.getData(), EPSILON);
        assertEquals(10.0, fixture.indicatorRepository.saved.getDeltaData(), EPSILON);
        assertEquals("{\"tableName\":\"ridership\",\"columnName\":\"passengers\",\"filters\":[]}", fixture.indicatorRepository.saved.getQuery());
    }

    @Test
    void queryUpdateRecomputesDataDeltaAndQuery() {
        Fixture fixture = new Fixture();
        LocalDate previousStart = START_DATE.minusDays(ChronoUnit.DAYS.between(START_DATE, END_DATE));
        fixture.indicatorRepository.whenAggregate(START_DATE, END_DATE, 200.0);
        fixture.indicatorRepository.whenAggregate(previousStart, START_DATE, 150.0);
        fixture.indicatorRepository.whenAggregate(LocalDate.of(2026, 1, 1), LocalDate.of(2026, 1, 31), 10.0);
        fixture.indicatorRepository.whenAggregate(LocalDate.of(2026, 2, 1), LocalDate.of(2026, 2, 28), 9.0);

        fixture.useCase().execute(1, new UpdateIndicatorDTO(
                null,
                null,
                IndicatorType.PERCENTAGE,
                null,
                null,
                null,
                null,
                null,
                null,
                null,
                null,
                null,
                new IndicatorFiltersDTO(new int[] { 2 }, new String[] { "Centro" })));

        assertEquals(4, fixture.indicatorRepository.calls.size());
        assertEquals(-10.0, fixture.indicatorRepository.saved.getData(), EPSILON);
        assertEquals(50.0, fixture.indicatorRepository.saved.getDeltaData(), EPSILON);
        assertArrayEquals(new int[] { 2 }, fixture.indicatorRepository.saved.getFilters().ids());
        assertArrayEquals(new String[] { "Centro" }, fixture.indicatorRepository.saved.getFilters().values());
    }

    @Test
    void dashboardUpdateRequiresTargetOwner() {
        Fixture fixture = new Fixture();
        UUID targetDashboardId = UUID.randomUUID();
        fixture.dashboardRepository.addDashboard(targetDashboardId, UUID.randomUUID());

        assertThrows(ForbiddenException.class, () -> fixture.useCase().execute(1, new UpdateIndicatorDTO(
                null,
                null,
                null,
                null,
                null,
                null,
                null,
                targetDashboardId,
                null,
                null,
                null,
                null,
                null)));
    }

    private static class Fixture {
        private final UUID ownerId = UUID.randomUUID();
        private final UUID dashboardId = UUID.randomUUID();
        private final FakeIndicatorRepository indicatorRepository = new FakeIndicatorRepository(existingIndicator(dashboardId));
        private final FakeSourceRepository sourceRepository = new FakeSourceRepository();
        private final FakeDashboardRepository dashboardRepository = new FakeDashboardRepository();

        Fixture() {
            dashboardRepository.addDashboard(dashboardId, ownerId);
        }

        UpdateIndicatorUseCase useCase() {
            AuthContext authContext = new AuthContext();
            User user = new User();
            user.setId(ownerId);
            authContext.setUser(user);
            return new UpdateIndicatorUseCase(
                    indicatorRepository,
                    dashboardRepository,
                    new DashboardAccessService(authContext),
                    new IndicatorMetricsCalculator(indicatorRepository, sourceRepository));
        }

        private static Indicator existingIndicator(UUID dashboardId) {
            return Indicator.builder()
                    .id(1)
                    .title("Pasajeros")
                    .subtitle("Enero a febrero")
                    .type(IndicatorType.NUMBER)
                    .data(100.0)
                    .relationship(Relationship.DIRECT)
                    .deltaData(10.0)
                    .operation(Operation.SUM)
                    .startDate(START_DATE)
                    .endDate(END_DATE)
                    .query("{\"tableName\":\"ridership\",\"columnName\":\"passengers\",\"filters\":[]}")
                    .dashboard(dashboardId)
                    .coordinate(new Coordinate(1, 2))
                    .source(1)
                    .table(2)
                    .column(null)
                    .filters(IndicatorFilterSelection.empty())
                    .build();
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
        private Indicator indicator;
        private Indicator saved;

        FakeIndicatorRepository(Indicator indicator) {
            this.indicator = indicator;
        }

        void whenAggregate(LocalDate startDate, LocalDate endDate, Double value) {
            aggregates.put(new AggregatePeriod(startDate, endDate), value);
        }

        @Override
        public Double aggregate(boolean year, String tableName, String columnName, Operation operation, LocalDate startDate, LocalDate endDate, Set<FilterMetadata> filters) {
            calls.add(new AggregateCall(year, tableName, columnName, operation, startDate, endDate, filters));
            return aggregates.getOrDefault(new AggregatePeriod(startDate, endDate), 0.0);
        }

        @Override
        public Indicator createIndicator(Indicator indicator) {
            throw new UnsupportedOperationException();
        }

        @Override
        public Indicator findIndicatorById(int id) {
            return indicator;
        }

        @Override
        public Indicator updateIndicator(Indicator indicator) {
            this.saved = indicator;
            this.indicator = indicator;
            return indicator;
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
            return new Metadata(
                    "ridership",
                    "passengers",
                    Set.of(new FilterMetadata("line", filters.values().length == 0 ? "" : filters.values()[0])));
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

    private static class FakeDashboardRepository implements DashboardRepository {
        private final Map<UUID, UUID> ownerIds = new HashMap<>();

        void addDashboard(UUID dashboardId, UUID ownerId) {
            ownerIds.put(dashboardId, ownerId);
        }

        @Override
        public CreateDashboardResponseDTO createDashboard(Dashboard dashboard) {
            throw new UnsupportedOperationException();
        }

        @Override
        public List<GetDashboardsResponseDTO> getPublicDashboards() {
            throw new UnsupportedOperationException();
        }

        @Override
        public List<GetUserDashboardsResponseDTO> getUserDashboards(User user) {
            throw new UnsupportedOperationException();
        }

        @Override
        public Dashboard findDashboardById(UUID uuid) {
            User owner = new User();
            owner.setId(ownerIds.get(uuid));
            return Dashboard.builder()
                    .id(uuid)
                    .owner(owner)
                    .isPublic(false)
                    .build();
        }

        @Override
        public CreateDashboardResponseDTO updateDashboard(Dashboard dashboard) {
            throw new UnsupportedOperationException();
        }

        @Override
        public void deleteDashboardById(UUID id) {
            throw new UnsupportedOperationException();
        }

        @Override
        public AddTagResponseDTO addTag(UUID id, int tagId) {
            throw new UnsupportedOperationException();
        }

        @Override
        public void removeTag(UUID id, int tagId) {
            throw new UnsupportedOperationException();
        }

        @Override
        public Dashboard updateLayout(UUID id, List<DashboardLayoutItem> items) {
            throw new UnsupportedOperationException();
        }
    }
}
