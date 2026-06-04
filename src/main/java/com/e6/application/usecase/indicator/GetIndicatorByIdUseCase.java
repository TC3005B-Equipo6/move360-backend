package com.e6.application.usecase.indicator;

import com.e6.domain.model.Indicator.Indicator;
import com.e6.domain.repository.IndicatorRepository;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class GetIndicatorByIdUseCase {

    private final IndicatorRepository indicatorRepository;

    public GetIndicatorByIdUseCase(IndicatorRepository indicatorRepository) {
        this.indicatorRepository = indicatorRepository;
    }

    public Indicator execute(int id){
        return indicatorRepository.findIndicatorById(id);
    }
}
