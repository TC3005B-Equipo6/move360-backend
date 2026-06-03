package com.e6.domain.model.source;

import java.util.List;

public record Filter(
        int id,
        String displayName,
        String columnName,
        List<String> values
) {
}
