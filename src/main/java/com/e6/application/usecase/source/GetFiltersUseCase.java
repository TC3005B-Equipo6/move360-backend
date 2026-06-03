package com.e6.application.usecase.source;

import com.e6.application.dto.source.FilterResponseDTO;
import com.e6.domain.repository.SourceRepository;
import jakarta.enterprise.context.ApplicationScoped;

import java.util.List;

@ApplicationScoped
public class GetFiltersUseCase {

    private final SourceRepository sourceRepository;

    public GetFiltersUseCase(SourceRepository sourceRepository) {
        this.sourceRepository = sourceRepository;
    }

    public List<FilterResponseDTO> execute(int id){
        return sourceRepository.getFilters(id);
    }
}
