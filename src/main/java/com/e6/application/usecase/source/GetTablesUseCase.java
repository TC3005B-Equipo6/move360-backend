package com.e6.application.usecase.source;

import com.e6.application.dto.source.SourceItemResponseDTO;
import com.e6.domain.repository.SourceRepository;
import jakarta.enterprise.context.ApplicationScoped;

import java.util.List;

@ApplicationScoped
public class GetTablesUseCase {

    private final SourceRepository sourceRepository;

    public GetTablesUseCase(SourceRepository sourceRepository) {
        this.sourceRepository = sourceRepository;
    }

    public List<SourceItemResponseDTO> execute(int id){
        return sourceRepository.getTables(id);
    }
}
