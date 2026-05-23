package com.e6.application.usecase.color;

import com.e6.application.dto.color.UpdateColorDTO;
import com.e6.domain.model.Color;
import com.e6.domain.model.Tag;
import com.e6.domain.repository.ColorRepository;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class UpdateColorUseCase {

    private final ColorRepository colorRepository;

    public UpdateColorUseCase(ColorRepository colorRepository) {
        this.colorRepository = colorRepository;
    }

    public Color execute(UpdateColorDTO updateColorDTO){
        Color color = Color.builder()
                .id(updateColorDTO.id())
                .name(updateColorDTO.name())
                .hex(updateColorDTO.hex())
                .build();

        return colorRepository.updateColor(color);
    }
}
