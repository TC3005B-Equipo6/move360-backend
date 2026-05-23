package com.e6.application.usecase.color;

import com.e6.domain.model.Color;
import com.e6.domain.repository.ColorRepository;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class GetColorByIdUseCase {

    private final ColorRepository colorRepository;

    public GetColorByIdUseCase(ColorRepository colorRepository) {
        this.colorRepository = colorRepository;
    }

    public Color execute(int id){
        return colorRepository.getColorById(id);
    }
}
