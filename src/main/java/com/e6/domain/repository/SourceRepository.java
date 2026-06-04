package com.e6.domain.repository;

import com.e6.application.dto.indicator.IndicatorFiltersDTO;
import com.e6.application.dto.source.*;
import com.e6.domain.model.source.Metadata;

import java.util.List;
import java.util.Set;

public interface SourceRepository {
    List<SourceItemResponseDTO> getSource();
    List<SourceItemResponseDTO> getTables(int id);
    List<SourceItemResponseDTO> getColumns(int id);
    List<FilterResponseDTO> getFilters(int id);
    Metadata getMetadata(int sourceId, int tableId, int columnId, IndicatorFiltersDTO filters);
}
