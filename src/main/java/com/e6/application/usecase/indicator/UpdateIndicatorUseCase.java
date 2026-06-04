package com.e6.application.usecase.indicator;

import com.e6.application.dto.indicator.UpdateIndicatorDTO;
import com.e6.application.dto.indicator.UpdateIndicatorResponseDTO;
import com.e6.domain.model.Indicator.Indicator;
import com.e6.domain.repository.IndicatorRepository;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class UpdateIndicatorUseCase {

    private final IndicatorRepository indicatorRepository;

    public UpdateIndicatorUseCase(IndicatorRepository indicatorRepository) {
        this.indicatorRepository = indicatorRepository;
    }

    public UpdateIndicatorResponseDTO execute(int id, UpdateIndicatorDTO updateIndicatorDTO){
        Indicator indicator = new Indicator();
        return indicatorRepository.updateIndicator(indicator);
    }
}
