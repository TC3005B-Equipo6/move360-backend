package com.e6.infrastructure.repository;

import com.e6.domain.exception.ColorNotFoundException;
import com.e6.domain.exception.TagNotFoundException;
import com.e6.domain.model.Color;
import com.e6.domain.repository.ColorRepository;
import com.e6.infrastructure.entity.ColorEntity;
import com.e6.infrastructure.mapper.ColorMapper;
import io.quarkus.hibernate.orm.panache.PanacheRepositoryBase;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.transaction.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@ApplicationScoped
public class ColorRepositoryImpl implements ColorRepository, PanacheRepositoryBase<ColorEntity, Integer> {
    @Override
    @Transactional
    public Color createColor(Color color) {
        ColorEntity entity = ColorMapper.toEntity(color);
        persist(entity);
        return ColorMapper.toDomain(entity);
    }

    @Override
    public List<Color> getColors() {
        return listAll().stream().map(ColorMapper::toDomain).collect(Collectors.toList());
    }

    @Override
    public Color getColorById(int id) {
        return ColorMapper.toDomain(findByIdOptional(id)
                .orElseThrow(() -> new ColorNotFoundException(String.valueOf(id))));
    }

    @Override
    @Transactional
    public Color updateColor(Color color) {
        ColorEntity colorEntity = findByIdOptional(color.getId())
                .orElseThrow(() -> new ColorNotFoundException(String.valueOf(color.getId())));

        if (color.getName() != null)
            colorEntity.setName(color.getName());
        if (color.getHex() != null)
            colorEntity.setHex(color.getHex());
        return ColorMapper.toDomain(colorEntity);
    }

    @Override
    @Transactional
    public void deleteColorById(int id) {
        boolean deleted = deleteById(id);
        if (!deleted) throw new ColorNotFoundException(String.valueOf(id));
    }
}
