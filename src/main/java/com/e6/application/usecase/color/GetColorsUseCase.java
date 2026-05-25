package com.e6.application.usecase.color;

import com.e6.domain.model.Color;
import com.e6.domain.repository.ColorRepository;
import jakarta.enterprise.context.ApplicationScoped;

import java.util.List;


@ApplicationScoped
public class GetColorsUseCase {

    private final ColorRepository colorRepository;

    public GetColorsUseCase(ColorRepository colorRepository) {
        this.colorRepository = colorRepository;
    }

    public List<Color> execute(){
        return colorRepository.getColors();
    }
}
