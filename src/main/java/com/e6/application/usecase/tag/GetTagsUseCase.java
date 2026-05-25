package com.e6.application.usecase.tag;

import com.e6.domain.model.Tag;
import com.e6.domain.repository.TagRepository;
import jakarta.enterprise.context.ApplicationScoped;

import java.util.List;

@ApplicationScoped
public class GetTagsUseCase {

    private final TagRepository tagRepository;

    public GetTagsUseCase(TagRepository tagRepository) {
        this.tagRepository = tagRepository;
    }

    public List<Tag> execute(){
        return tagRepository.getTags();
    }
}
