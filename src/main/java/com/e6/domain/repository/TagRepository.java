package com.e6.domain.repository;

import com.e6.domain.model.Tag;

import java.util.List;
import java.util.UUID;

public interface TagRepository {
    Tag createTag(Tag tag);

    List<Tag> getTags();

    List<Tag> getDashboardTags(UUID id);

    Tag updateTag(Tag tag);

    void deleteTagById(int id);
}
