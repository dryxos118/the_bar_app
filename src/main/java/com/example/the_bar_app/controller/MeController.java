package com.example.the_bar_app.controller;

import com.example.the_bar_app.dto.drink.DrinkDto;
import com.example.the_bar_app.dto.user.UserPreferencesDto;
import com.example.the_bar_app.dto.user.UserSettingsDto;
import com.example.the_bar_app.dto.user.UserSummaryDto;
import com.example.the_bar_app.service.CurrentUserService;
import com.example.the_bar_app.service.impl.IUserPreferencesService;
import com.example.the_bar_app.service.impl.IUserService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/me")
@Tag(name = "Me")
@RequiredArgsConstructor
public class MeController {
    private final IUserService userService;
    private final IUserPreferencesService preferencesService;
    private final CurrentUserService current;

    @GetMapping("/info")
    public ResponseEntity<UserSummaryDto> getInfo() {
        UserSummaryDto dto = userService.loadUserByUsername(current.usernameOrEmail());
        return ResponseEntity.ok(dto);
    }

    @GetMapping("/preferences")
    public ResponseEntity<UserPreferencesDto> getPreferences(){
        UserPreferencesDto dto = preferencesService.getPreferences(current.id());
        return ResponseEntity.ok(dto);
    }

    @PatchMapping("/settings")
    public ResponseEntity<UserSettingsDto> updatePreferences(@RequestBody UserSettingsDto dto){
        UserSettingsDto updateDto = preferencesService.updatePreferences(current.id(), dto);
        return ResponseEntity.ok(updateDto);
    }

    @GetMapping("/favorites")
    public ResponseEntity<List<DrinkDto>> getFavorites(){
        List<DrinkDto> favIds = preferencesService.listFavorites(current.id());
        return ResponseEntity.ok(favIds);
    }

    @PostMapping("/addFavorites/{id}")
    public ResponseEntity<Boolean> toggleFavorites(@PathVariable Long id){
        Boolean success = preferencesService.toggleFavorite(current.id(), id);
        return ResponseEntity.ok(success);
    }
}
