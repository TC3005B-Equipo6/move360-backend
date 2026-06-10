package com.e6.domain.model.graph;

import java.util.List;
import java.util.Map;

public record GraphSnapshot(
        Double delta,
        List<Map<String, Object>> data,
        List<GraphSeries> series
) {
}
