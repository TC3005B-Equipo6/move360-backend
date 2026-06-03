package com.e6.domain.repository;

import com.e6.application.dto.source.*;

import java.util.List;

public interface SourceRepository {
    List<SourceItemResponseDTO> getSource();
    List<SourceItemResponseDTO> getTables(int id);
    List<SourceItemResponseDTO> getColumns(int id);
    List<FilterResponseDTO> getFilters(int id);
}
