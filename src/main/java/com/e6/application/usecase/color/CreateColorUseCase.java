package com.e6.application.usecase.color;

import com.e6.application.dto.color.CreateColorDTO;
import com.e6.domain.model.Color;
import com.e6.domain.repository.ColorRepository;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class CreateColorUseCase {

    private final ColorRepository colorRepository;

    public CreateColorUseCase(ColorRepository colorRepository) {
        this.colorRepository = colorRepository;
    }

    public Color execute(CreateColorDTO createColorDTO) {
        Color color = Color.builder()
                .name(createColorDTO.name())
                .hex(createColorDTO.hex())
                .build();

        return colorRepository.createColor(color);
    }

}
