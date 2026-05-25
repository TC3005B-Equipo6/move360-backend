package com.e6.application.usecase.tag;

import com.e6.domain.repository.TagRepository;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class DeleteTagByIdUseCase {

    private final TagRepository tagRepository;

    public DeleteTagByIdUseCase(TagRepository tagRepository) {
        this.tagRepository = tagRepository;
    }

    public void execute(int id){
        tagRepository.deleteTagById(id);
    }
}
