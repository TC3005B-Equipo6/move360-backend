package com.e6.domain.model.source;

import java.util.Set;

public record Metadata(
        String tableName,
        String columnName,
        Set<FilterMetadata> filters
) {
}
