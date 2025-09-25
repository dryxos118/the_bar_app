package com.example.the_bar_app.dto;

import com.example.the_bar_app.entity.RoleName;

public record UserSummaryDto(
        Long id,
        String username,
        String email,
        RoleName role,
        boolean enabled
) {
}