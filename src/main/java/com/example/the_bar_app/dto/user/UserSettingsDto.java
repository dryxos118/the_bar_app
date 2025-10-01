package com.example.the_bar_app.dto.user;

public record UserSettingsDto(
        String avatarId,
        String theme,
        String currency,
        Boolean showOutOfStock) {
}