package com.e6.application.usecase.color;

import com.e6.domain.repository.ColorRepository;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class DeleteColorByIdUseCase {

    private final ColorRepository colorRepository;

    public DeleteColorByIdUseCase(ColorRepository colorRepository) {
        this.colorRepository = colorRepository;
    }

    public void execute(int id){
        colorRepository.deleteColorById(id);
    }
}
