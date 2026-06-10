package com.e6.domain.model.source;

import java.util.List;

public record Source(
        int id,
        String name,
        List<Table>tables
) {
}
