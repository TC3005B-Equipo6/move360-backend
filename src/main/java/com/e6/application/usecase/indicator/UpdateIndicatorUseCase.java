package com.e6.application.usecase.indicator;

import com.e6.application.dto.indicator.CreateIndicatorResponseDTO;
import com.e6.application.dto.indicator.UpdateIndicatorDTO;
import com.e6.application.dto.indicator.UpdateIndicatorResponseDTO;
import com.e6.domain.model.Indicator.Indicator;
import com.e6.domain.model.source.Metadata;
import com.e6.domain.repository.IndicatorRepository;
import jakarta.enterprise.context.ApplicationScoped;

import java.util.Objects;

@ApplicationScoped
public class UpdateIndicatorUseCase {

    private final IndicatorRepository indicatorRepository;

    public UpdateIndicatorUseCase(IndicatorRepository indicatorRepository) {
        this.indicatorRepository = indicatorRepository;
    }

    public CreateIndicatorResponseDTO execute(int id, UpdateIndicatorDTO updateIndicatorDTO){
        Indicator indicator = Indicator.builder()
                .id(id)
                .title(updateIndicatorDTO.title())
                .subtitle(updateIndicatorDTO.subtitle())
                .relationship(updateIndicatorDTO.relationship())
                .coordinate(updateIndicatorDTO.coordinate())
                .build();
        return indicatorRepository.updateIndicator(indicator);
    }
}
