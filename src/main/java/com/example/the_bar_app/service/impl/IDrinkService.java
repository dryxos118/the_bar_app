package com.example.the_bar_app.service.impl;

import com.example.the_bar_app.dto.DrinkDto;

import java.util.List;

public interface IDrinkService {
    DrinkDto get(Long id);

    List<DrinkDto> getAll();

    boolean create(DrinkDto dto);

    boolean replace(Long id, DrinkDto dto);

    boolean update(Long id, DrinkDto dto);

    boolean delete(Long id);
}
