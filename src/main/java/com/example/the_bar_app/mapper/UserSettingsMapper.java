package com.example.the_bar_app.mapper;

import com.example.the_bar_app.dto.user.UserPreferencesDto;
import com.example.the_bar_app.dto.user.UserSettingsDto;;
import com.example.the_bar_app.entity.user.Theme;
import com.example.the_bar_app.entity.user.UserSettings;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring", imports = {Theme.class})
public interface UserSettingsMapper {
    @Mapping(target = "theme",
            expression = "java(entity.getTheme().name())")
    UserSettingsDto toDto(UserSettings entity);

    @Mapping(target = "theme",
            expression = "java(dto.theme()!=null? Theme.valueOf(dto.theme().toUpperCase()):entity.getTheme())")
    void updateFromDto(UserSettingsDto dto, @MappingTarget UserSettings entity);

    default UserPreferencesDto toPreferences(UserSettings s) {
        return new UserPreferencesDto(
                toDto(s),
                s.getFavoriteDrinkIds().stream().toList()
        );
    }
}
