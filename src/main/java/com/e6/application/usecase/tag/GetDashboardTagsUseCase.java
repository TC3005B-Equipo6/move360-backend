package com.e6.application.usecase.tag;

import com.e6.domain.model.Tag;
import com.e6.domain.repository.TagRepository;
import jakarta.enterprise.context.ApplicationScoped;

import java.util.List;
import java.util.UUID;

@ApplicationScoped
public class GetDashboardTagsUseCase {

    private final TagRepository tagRepository;

    public GetDashboardTagsUseCase(TagRepository tagRepository) {
        this.tagRepository = tagRepository;
    }

    public List<Tag> execute(UUID id){
        return tagRepository.getDashboardTags(id);
    }
}
