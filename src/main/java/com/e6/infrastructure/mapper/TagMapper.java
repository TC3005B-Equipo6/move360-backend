package com.e6.infrastructure.mapper;

import com.e6.domain.model.Tag;
import com.e6.infrastructure.entity.TagEntity;

import java.util.Set;
import java.util.stream.Collectors;

public final class TagMapper {

    public static Tag toDomain(TagEntity tagEntity) {
        return Tag.builder()
                .id(tagEntity.getId())
                .name(tagEntity.getName())
                .color(ColorMapper.toDomain(tagEntity.getColor()))
                .dashboards(DashboardMapper.toDomainSetWithoutTags(tagEntity.getDashboards()))
                .build();
    }

    public static Tag toDomainWithoutDashboards(TagEntity tagEntity) {
        return Tag.builder()
                .id(tagEntity.getId())
                .name(tagEntity.getName())
                .color(ColorMapper.toDomain(tagEntity.getColor()))
                .build();
    }

    public static Set<Tag> toDomainSet(Set<TagEntity> entities) {
        return entities.stream()
                .map(TagMapper::toDomain)
                .collect(Collectors.toSet());
    }

    public static Set<Tag> toDomainSetWithoutDashboards(Set<TagEntity> entities) {
        return entities.stream()
                .map(TagMapper::toDomainWithoutDashboards)
                .collect(Collectors.toSet());
    }

    public static TagEntity toEntity(Tag tag) {
        TagEntity tagEntity = new TagEntity();
        tagEntity.setName(tag.getName());
        tagEntity.setDashboards(DashboardMapper.toEntitySet(tag.getDashboards()));
        return tagEntity;
    }

    public static Set<TagEntity> toEntitySet(Set<Tag> tags) {
        return tags.stream()
                .map(TagMapper::toEntity)
                .collect(Collectors.toSet());
    }

}
