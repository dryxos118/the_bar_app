package com.example.the_bar_app.service;

import com.example.the_bar_app.api.AppException;
import com.example.the_bar_app.api.ErrorType;
import com.example.the_bar_app.dto.drink.DrinkDto;
import com.example.the_bar_app.dto.user.UserPreferencesDto;
import com.example.the_bar_app.dto.user.UserSettingsDto;
import com.example.the_bar_app.entity.drink.Drink;
import com.example.the_bar_app.entity.user.Theme;
import com.example.the_bar_app.entity.user.User;
import com.example.the_bar_app.entity.user.UserSettings;
import com.example.the_bar_app.mapper.DrinkMapper;
import com.example.the_bar_app.mapper.UserSettingsMapper;
import com.example.the_bar_app.repository.DrinkRepository;
import com.example.the_bar_app.repository.UserRepository;
import com.example.the_bar_app.repository.UserSettingsRepository;
import com.example.the_bar_app.service.impl.IUserPreferencesService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserPreferencesService implements IUserPreferencesService {
    private final UserSettingsRepository settingsRepo;
    private final UserRepository userRepo;
    private final UserSettingsMapper settingsMapper;
    private final DrinkRepository drinkRepo;
    private final DrinkMapper drinkMapper;

    private UserSettings getOrCreate(Long userId) {
        return settingsRepo.findById(userId).orElseGet(() -> {
            User user = userRepo.findById(userId)
                    .orElseThrow(() -> new AppException(ErrorType.NOT_FOUND, "User not found"));
            UserSettings settings = new UserSettings();
            settings.setUser(user);
            settings.setTheme(Theme.DARK);
            settings.setCurrency("EUR");
            return settingsRepo.save(settings);
        });
    }


    @Override
    @Transactional
    public UserPreferencesDto getPreferences(Long userId) {
        return settingsMapper.toPreferences(getOrCreate(userId));
    }

    @Override
    @Transactional
    public UserSettingsDto updatePreferences(Long userId, UserSettingsDto dto) {
        UserSettings userSettings = getOrCreate(userId);
        settingsMapper.updateFromDto(dto, userSettings);
        return settingsMapper.toDto(settingsRepo.save(userSettings));
    }

    @Override
    @Transactional
    public Boolean toggleFavorite(Long userId, Long drinkId) {
        UserSettings s = getOrCreate(userId);
        if (s.getFavoriteDrinkIds().contains(drinkId)) {
            s.getFavoriteDrinkIds().remove(drinkId);
        } else {
            s.getFavoriteDrinkIds().add(drinkId);
        }
        return true;
    }

    @Override
    @Transactional
    public List<DrinkDto> listFavorites(Long userId) {
        UserSettings settings = getOrCreate(userId);
        List<Long> ids = settings.getFavoriteDrinkIds().stream().toList();
        if(ids.isEmpty()) return List.of();
        List<Drink> drinks = drinkRepo.findAllByIdIn(ids);
        return drinks.stream().map(drinkMapper::toDto).toList();
    }
}
