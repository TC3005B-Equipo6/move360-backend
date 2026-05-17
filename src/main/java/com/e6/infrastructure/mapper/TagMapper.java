package com.e6.infrastructure.mapper;

import com.e6.domain.model.Tag;
import com.e6.infrastructure.entity.TagEntity;

import java.util.Set;
import java.util.stream.Collectors;

public final class TagMapper {

    public static Tag toDomain(TagEntity tagEntity){
        Tag tag = new Tag();
        tag.setId(tagEntity.getId());
        tag.setName(tagEntity.getName());
        tag.setDashboards(DashboardMapper.toDomainSet(tagEntity.getDashboards()));
        tag.setColor(ColorMapper.toDomain(tagEntity.getColor()));
        return tag;
    }

    public static Set<Tag> toDomainSet(Set<TagEntity> entities){
        return entities.stream()
                .map(TagMapper::toDomain)
                .collect(Collectors.toSet());
    }

    public static TagEntity toEntity(Tag tag){
        TagEntity tagEntity = new TagEntity();
        tagEntity.setId(tag.getId());
        tagEntity.setName(tag.getName());
        tagEntity.setDashboards(DashboardMapper.toEntitySet(tag.getDashboards()));
        tagEntity.setColor(ColorMapper.toEntity(tag.getColor()));
        return tagEntity;
    }

    public static Set<TagEntity> toEntitySet(Set<Tag> tags){
        return tags.stream()
                .map(TagMapper::toEntity)
                .collect(Collectors.toSet());
    }

}
