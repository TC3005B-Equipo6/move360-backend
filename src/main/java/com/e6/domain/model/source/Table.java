package com.e6.domain.model.source;

import java.util.List;

public record Table(
        int id,
        String displayName,
        String tableName,
        List<Column> columns,
        List<Filter> filters
) {
}
