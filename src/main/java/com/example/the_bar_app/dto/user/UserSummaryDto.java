package com.example.the_bar_app.dto.user;

import com.example.the_bar_app.entity.user.RoleName;

public record UserSummaryDto(
        Long id,
        String username,
        String email,
        RoleName role,
        boolean enabled
) {
}