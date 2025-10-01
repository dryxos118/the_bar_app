package com.example.the_bar_app.service.impl;

import com.example.the_bar_app.dto.drink.DrinkDto;
import com.example.the_bar_app.dto.user.UserPreferencesDto;
import com.example.the_bar_app.dto.user.UserSettingsDto;

import java.util.List;

public interface IUserPreferencesService {
    UserPreferencesDto getPreferences(Long userId);

    UserSettingsDto updatePreferences(Long userId, UserSettingsDto dto);

    Boolean toggleFavorite(Long userId, Long drinkId);

    List<DrinkDto> listFavorites(Long userId);
}
