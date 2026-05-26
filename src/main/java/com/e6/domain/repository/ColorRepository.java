package com.e6.domain.repository;

import com.e6.domain.model.Color;

import java.util.List;

public interface ColorRepository {
    Color createColor(Color color);

    List<Color> getColors();

    Color getColorById(int id);

    Color updateColor(Color color);

    void deleteColorById(int id);
}
