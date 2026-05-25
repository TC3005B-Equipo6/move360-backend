package com.e6.application.usecase.tag;

import com.e6.application.dto.tag.CreateTagDTO;
import com.e6.domain.model.Color;
import com.e6.domain.model.Tag;
import com.e6.domain.repository.TagRepository;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class CreateTagUseCase {

    private final TagRepository tagRepository;

    public CreateTagUseCase(TagRepository tagRepository) {
        this.tagRepository = tagRepository;
    }

    public Tag execute(CreateTagDTO createTagDTO){
        Tag tag = Tag.builder()
                .name(createTagDTO.name())
                .color(Color.builder().id(createTagDTO.colorId()).build())
                .build();
        return tagRepository.createTag(tag);
    }
}
