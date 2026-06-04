package com.e6.application.usecase.indicator;

import com.e6.domain.repository.IndicatorRepository;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class DeleteIndicatorByIdUseCase {

    private final IndicatorRepository indicatorRepository;

    public DeleteIndicatorByIdUseCase(IndicatorRepository indicatorRepository) {
        this.indicatorRepository = indicatorRepository;
    }

    public void execute(int id){
        indicatorRepository.deleteIndicatorById(id);
    }
}
