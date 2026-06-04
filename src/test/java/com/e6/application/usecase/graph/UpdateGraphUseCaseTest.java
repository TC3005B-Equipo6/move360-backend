package com.e6.application.usecase.graph;

import com.e6.application.dto.graph.UpdateGraphDTO;
import com.e6.application.service.DashboardAccessService;
import com.e6.domain.model.Dashboard;
import com.e6.domain.model.User;
import com.e6.domain.model.graph.Graph;
import com.e6.domain.model.graph.GraphOperation;
import com.e6.domain.model.graph.GraphSeries;
import com.e6.domain.model.graph.GraphSize;
import com.e6.domain.model.graph.GraphSnapshot;
import com.e6.domain.model.graph.GraphType;
import com.e6.domain.repository.DashboardRepository;
import com.e6.domain.repository.GraphDataQuery;
import com.e6.domain.repository.GraphRepository;
import com.e6.infrastructure.security.AuthContext;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Map;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;

class UpdateGraphUseCaseTest {

    @Test
    void coordinateOnlyUpdateSkipsSnapshotRecompute() {
        Fixture fixture = new Fixture();
        UpdateGraphUseCase useCase = fixture.useCase();

        useCase.execute(1, new UpdateGraphDTO(
                null,
                null,
                null,
                null,
                null,
                null,
                null,
                null,
                null,
                null,
                null,
                new com.e6.domain.model.Indicator.Coordinate(9, 9)));

        assertEquals(0, fixture.graphDataQuery.calls);
        assertEquals(new com.e6.domain.model.Indicator.Coordinate(9, 9), fixture.graphRepository.saved.getCoordinate());
    }

    @Test
    void metricUpdateRecomputesSnapshot() {
        Fixture fixture = new Fixture();
        UpdateGraphUseCase useCase = fixture.useCase();

        useCase.execute(1, new UpdateGraphDTO(
                null,
                null,
                null,
                null,
                null,
                List.of("operations"),
                null,
                null,
                null,
                null,
                null,
                null));

        assertEquals(1, fixture.graphDataQuery.calls);
        assertEquals(42.0, fixture.graphRepository.saved.getDelta());
        assertEquals(List.of("operations"), fixture.graphRepository.saved.getMetricColumns());
    }

    private static class Fixture {
        private final UUID ownerId = UUID.randomUUID();
        private final UUID dashboardId = UUID.randomUUID();
        private final FakeGraphRepository graphRepository = new FakeGraphRepository(existingGraph(dashboardId));
        private final FakeDashboardRepository dashboardRepository = new FakeDashboardRepository(ownerId, dashboardId);
        private final FakeGraphDataQuery graphDataQuery = new FakeGraphDataQuery();

        UpdateGraphUseCase useCase() {
            AuthContext authContext = new AuthContext();
            User user = new User();
            user.setId(ownerId);
            authContext.setUser(user);
            return new UpdateGraphUseCase(
                    graphRepository,
                    dashboardRepository,
                    graphDataQuery,
                    new DashboardAccessService(authContext));
        }

        private static Graph existingGraph(UUID dashboardId) {
            return Graph.builder()
                    .id(1)
                    .dashboardId(dashboardId)
                    .size(GraphSize.CHART_MD)
                    .type(GraphType.BAR)
                    .sourceId(0)
                    .tableId(0)
                    .dimensionColumn("month")
                    .metricColumns(List.of("passengers"))
                    .operation(GraphOperation.SUM)
                    .compareEnabled(false)
                    .startMonth("2026-01")
                    .endMonth("2026-02")
                    .coordinate(new com.e6.domain.model.Indicator.Coordinate(1, 1))
                    .delta(1.0)
                    .data(List.of(Map.of("name", "2026-01", "passengers", 10.0)))
                    .series(List.of(new GraphSeries(1, "passengers", "Pasajeros", "#2563EB", List.of())))
                    .build();
        }
    }

    private static class FakeGraphRepository implements GraphRepository {
        private Graph graph;
        private Graph saved;

        FakeGraphRepository(Graph graph) {
            this.graph = graph;
        }

        @Override
        public Graph createGraph(Graph graph) {
            throw new UnsupportedOperationException();
        }

        @Override
        public Graph findGraphById(int id) {
            return graph;
        }

        @Override
        public Graph updateGraph(Graph graph) {
            this.saved = graph;
            this.graph = graph;
            return graph;
        }

        @Override
        public void deleteGraphById(int id) {
            throw new UnsupportedOperationException();
        }
    }

    private static class FakeDashboardRepository implements DashboardRepository {
        private final UUID ownerId;
        private final UUID dashboardId;

        FakeDashboardRepository(UUID ownerId, UUID dashboardId) {
            this.ownerId = ownerId;
            this.dashboardId = dashboardId;
        }

        @Override
        public com.e6.application.dto.dashboard.CreateDashboardResponseDTO createDashboard(Dashboard dashboard) {
            throw new UnsupportedOperationException();
        }

        @Override
        public java.util.List<com.e6.application.dto.dashboard.GetDashboardsResponseDTO> getPublicDashboards() {
            throw new UnsupportedOperationException();
        }

        @Override
        public java.util.List<com.e6.application.dto.dashboard.GetUserDashboardsResponseDTO> getUserDashboards(User user) {
            throw new UnsupportedOperationException();
        }

        @Override
        public Dashboard findDashboardById(UUID uuid) {
            User owner = new User();
            owner.setId(ownerId);
            return Dashboard.builder()
                    .id(dashboardId)
                    .owner(owner)
                    .isPublic(false)
                    .build();
        }

        @Override
        public com.e6.application.dto.dashboard.CreateDashboardResponseDTO updateDashboard(Dashboard dashboard) {
            throw new UnsupportedOperationException();
        }

        @Override
        public void deleteDashboardById(UUID id) {
            throw new UnsupportedOperationException();
        }

        @Override
        public com.e6.application.dto.dashboard.AddTagResponseDTO addTag(UUID id, int tagId) {
            throw new UnsupportedOperationException();
        }

        @Override
        public void removeTag(UUID id, int tagId) {
            throw new UnsupportedOperationException();
        }

        @Override
        public Dashboard updateLayout(UUID id, java.util.List<com.e6.domain.model.DashboardLayoutItem> items) {
            throw new UnsupportedOperationException();
        }
    }

    private static class FakeGraphDataQuery implements GraphDataQuery {
        private int calls;

        @Override
        public GraphSnapshot calculate(Graph graph) {
            calls++;
            return new GraphSnapshot(
                    42.0,
                    List.of(Map.of("name", "2026-01", "operations", 99.0)),
                    List.of(new GraphSeries(0, "operations", "Operaciones", "#059669", List.of()))
            );
        }
    }
}
