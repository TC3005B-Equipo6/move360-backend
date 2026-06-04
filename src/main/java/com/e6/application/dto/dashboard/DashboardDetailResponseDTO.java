package com.e6.application.dto.dashboard;

import com.e6.application.dto.graph.GraphResponseDTO;
import com.e6.application.dto.indicator.IndicatorFiltersDTO;
import com.e6.domain.model.Dashboard;
import com.e6.domain.model.DashboardItemKind;
import com.e6.domain.model.Indicator.Coordinate;
import com.e6.domain.model.Indicator.Indicator;
import com.e6.domain.model.Indicator.Operation;
import com.e6.domain.model.Indicator.Relationship;
import com.e6.domain.model.Tag;
import com.e6.domain.model.graph.Graph;
import com.e6.domain.model.graph.GraphOperation;
import com.e6.domain.model.graph.GraphSize;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Stream;

public record DashboardDetailResponseDTO(
        UUID id,
        String ownerName,
        String title,
        String description,
        LocalDateTime createdAt,
        boolean isPublic,
        List<TagDTO> tags,
        List<ItemDTO> items
) {
    public static DashboardDetailResponseDTO from(Dashboard dashboard) {
        List<ItemDTO> items = Stream.concat(
                        dashboard.getIndicators().stream().map(ItemDTO::fromIndicator),
                        dashboard.getGraphs().stream().map(ItemDTO::fromGraph))
                .sorted(Comparator.comparing(ItemDTO::itemId))
                .toList();

        return new DashboardDetailResponseDTO(
                dashboard.getId(),
                dashboard.getOwner().getFirstName() + " " + dashboard.getOwner().getPaternalSurname(),
                dashboard.getTitle(),
                dashboard.getDescription(),
                dashboard.getCreatedAt(),
                dashboard.isPublic(),
                dashboard.getTags().stream().map(TagDTO::from).toList(),
                items
        );
    }

    public record TagDTO(
            int id,
            String name,
            Integer colorId,
            String colorName,
            String colorHex
    ) {
        static TagDTO from(Tag tag) {
            return new TagDTO(
                    tag.getId(),
                    tag.getName(),
                    tag.getColor() == null ? null : tag.getColor().getId(),
                    tag.getColor() == null ? null : tag.getColor().getName(),
                    tag.getColor() == null ? null : tag.getColor().getHex()
            );
        }
    }

    public record ItemDTO(
            String itemId,
            DashboardItemKind kind,
            int resourceId,
            String type,
            Coordinate coordinate,
            GraphItemDTO graph,
            IndicatorItemDTO indicator
    ) {
        static ItemDTO fromGraph(Graph graph) {
            return new ItemDTO(
                    "graph:" + graph.getId(),
                    DashboardItemKind.GRAPH,
                    graph.getId(),
                    graph.getType().name(),
                    graph.getCoordinate(),
                    GraphItemDTO.from(graph),
                    null
            );
        }

        static ItemDTO fromIndicator(Indicator indicator) {
            return new ItemDTO(
                    "indicator:" + indicator.getId(),
                    DashboardItemKind.INDICATOR,
                    indicator.getId(),
                    indicator.getType().name(),
                    indicator.getCoordinate(),
                    null,
                    IndicatorItemDTO.from(indicator)
            );
        }
    }

    public record GraphItemDTO(
            String title,
            String subtitle,
            GraphSize size,
            int sourceId,
            int tableId,
            String dimensionColumn,
            List<String> metricColumns,
            GraphOperation operation,
            boolean compareEnabled,
            Integer compareTableId,
            String startMonth,
            String endMonth,
            Double delta,
            List<Map<String, Object>> data,
            List<GraphResponseDTO.SeriesDTO> series
    ) {
        static GraphItemDTO from(Graph graph) {
            return new GraphItemDTO(
                    graph.getTitle(),
                    graph.getSubtitle(),
                    graph.getSize(),
                    graph.getSourceId(),
                    graph.getTableId(),
                    graph.getDimensionColumn(),
                    graph.getMetricColumns(),
                    graph.getOperation(),
                    graph.isCompareEnabled(),
                    graph.getCompareTableId(),
                    graph.getStartMonth(),
                    graph.getEndMonth(),
                    graph.getDelta(),
                    graph.getData(),
                    graph.getSeries().stream().map(GraphResponseDTO.SeriesDTO::from).toList()
            );
        }
    }

    public record IndicatorItemDTO(
            String title,
            String subtitle,
            Double data,
            Relationship relationship,
            Double deltaData,
            Operation operation,
            LocalDate startDate,
            LocalDate endDate,
            int sourceId,
            int tableId,
            Integer columnId,
            IndicatorFiltersDTO filters
    ) {
        static IndicatorItemDTO from(Indicator indicator) {
            return new IndicatorItemDTO(
                    indicator.getTitle(),
                    indicator.getSubtitle(),
                    indicator.getData(),
                    indicator.getRelationship(),
                    indicator.getDeltaData(),
                    indicator.getOperation(),
                    indicator.getStartDate(),
                    indicator.getEndDate(),
                    indicator.getSourceId(),
                    indicator.getTableId(),
                    indicator.getColumnId(),
                    IndicatorFiltersDTO.from(indicator.getFilters())
            );
        }
    }
}
