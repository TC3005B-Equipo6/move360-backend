package com.e6.application.usecase.source;

import com.e6.application.dto.source.SourceItemResponseDTO;
import com.e6.domain.repository.SourceRepository;
import jakarta.enterprise.context.ApplicationScoped;

import java.util.List;

@ApplicationScoped
public class GetSourcesUseCase {

    private final SourceRepository sourceRepository;

    public GetSourcesUseCase(SourceRepository sourceRepository) {
        this.sourceRepository = sourceRepository;
    }

    public List<SourceItemResponseDTO> execute(){
        return this.sourceRepository.getSource();
    }
}
