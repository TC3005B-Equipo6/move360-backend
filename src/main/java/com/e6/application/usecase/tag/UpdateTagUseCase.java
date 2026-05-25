package com.e6.application.usecase.tag;

import com.e6.application.dto.tag.UpdateTagDTO;
import com.e6.domain.model.Color;
import com.e6.domain.model.Tag;
import com.e6.domain.repository.TagRepository;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class UpdateTagUseCase {

    private final TagRepository tagRepository;

    public UpdateTagUseCase(TagRepository tagRepository) {
        this.tagRepository = tagRepository;
    }

    public Tag execute(int id, UpdateTagDTO updateTagDTO) {
        Tag tag = Tag.builder()
                .id(id)
                .name(updateTagDTO.name())
                .color(Color.builder().id(updateTagDTO.colorId()).build())
                .build();

        return tagRepository.updateTag(tag);
    }
}
