package com.example.the_bar_app.dto.user;

import java.util.List;

public record UserPreferencesDto(
        UserSettingsDto settings,
        List<Long> favoriteDrinkIds
) {
}
