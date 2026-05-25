package com.e6.infrastructure.repository;

import com.e6.domain.exception.ColorNotFoundException;
import com.e6.domain.exception.TagNotFoundException;
import com.e6.domain.model.Tag;
import com.e6.domain.repository.TagRepository;
import com.e6.infrastructure.entity.ColorEntity;
import com.e6.infrastructure.entity.TagEntity;
import com.e6.infrastructure.mapper.ColorMapper;
import com.e6.infrastructure.mapper.TagMapper;
import io.quarkus.hibernate.orm.panache.PanacheRepositoryBase;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.transaction.Transactional;
import org.hibernate.exception.ConstraintViolationException;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@ApplicationScoped
public class TagRepositoryImpl implements TagRepository, PanacheRepositoryBase<TagEntity, Integer> {

    @Override
    @Transactional
    public Tag createTag(Tag tag) {
        ColorEntity colorReference = new ColorEntity();
        try {
            TagEntity tagEntity = TagMapper.toEntity(tag);
            colorReference = getEntityManager().getReference(ColorEntity.class, tag.getColor().getId());
            tagEntity.setColor(colorReference);
            persist(tagEntity);
            return TagMapper.toDomain(tagEntity);
        } catch ( ConstraintViolationException e){
            throw new ColorNotFoundException(String.valueOf(colorReference.getId()));
        }
    }

    @Override
    public List<Tag> getTags() {
        return listAll().stream().map(TagMapper::toDomain).collect(Collectors.toList());
    }

    @Override
    public List<Tag> getDashboardTags(UUID id) {
        return find("SELECT t FROM TagEntity t JOIN t.dashboards d WHERE d.id = ?1", id)
                .stream()
                .map(TagMapper::toDomain)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public Tag updateTag(Tag tag) {
        ColorEntity colorReference = new ColorEntity();
        try {
            TagEntity tagEntity = findByIdOptional(tag.getId())
                    .orElseThrow(() -> new TagNotFoundException(String.valueOf(tag.getId())));

            if (tag.getName() != null)
                tagEntity.setName(tag.getName());
            if (tag.getColor().getId() != 0) {
                colorReference = getEntityManager().getReference(ColorEntity.class, tag.getColor().getId());
                tagEntity.setColor(colorReference);
            }
            return TagMapper.toDomain(tagEntity);
        } catch ( Exception e) {
            throw new ColorNotFoundException(String.valueOf(tag.getColor().getId()));
        }

    }

    @Override
    @Transactional
    public void deleteTagById(int id) {
        boolean deleted = deleteById(id);
        if (!deleted)
            throw new TagNotFoundException(String.valueOf(id));
    }
}
