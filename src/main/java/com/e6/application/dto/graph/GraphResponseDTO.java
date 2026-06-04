package com.e6.application.dto.graph;

import com.e6.domain.model.Indicator.Coordinate;
import com.e6.domain.model.graph.Graph;
import com.e6.domain.model.graph.GraphOperation;
import com.e6.domain.model.graph.GraphSeries;
import com.e6.domain.model.graph.GraphSize;
import com.e6.domain.model.graph.GraphType;

import java.util.List;
import java.util.Map;
import java.util.UUID;

public record GraphResponseDTO(
        String itemId,
        int id,
        UUID dashboardId,
        GraphSize size,
        GraphType type,
        int sourceId,
        int tableId,
        String dimensionColumn,
        List<String> metricColumns,
        GraphOperation operation,
        boolean compareEnabled,
        Integer compareTableId,
        String startMonth,
        String endMonth,
        Coordinate coordinate,
        Double delta,
        List<Map<String, Object>> data,
        List<SeriesDTO> series
) {
    public static GraphResponseDTO from(Graph graph) {
        return new GraphResponseDTO(
                "graph:" + graph.getId(),
                graph.getId(),
                graph.getDashboardId(),
                graph.getSize(),
                graph.getType(),
                graph.getSourceId(),
                graph.getTableId(),
                graph.getDimensionColumn(),
                graph.getMetricColumns(),
                graph.getOperation(),
                graph.isCompareEnabled(),
                graph.getCompareTableId(),
                graph.getStartMonth(),
                graph.getEndMonth(),
                graph.getCoordinate(),
                graph.getDelta(),
                graph.getData(),
                graph.getSeries().stream().map(SeriesDTO::from).toList()
        );
    }

    public record SeriesDTO(
            int id,
            String seriesKey,
            String label,
            String color,
            List<Map<String, Object>> data
    ) {
        public static SeriesDTO from(GraphSeries series) {
            return new SeriesDTO(
                    series.getId(),
                    series.getSeriesKey(),
                    series.getLabel(),
                    series.getColor(),
                    series.getData()
            );
        }
    }
}
