package com.example.the_bar_app.dto.auth;

import com.example.the_bar_app.entity.user.RoleName;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record RegisterDto(
        @NotBlank @Size(min = 3, max = 64) String username,
        @NotBlank @Email String email,
        @NotBlank @Size(min = 8) String password,
        RoleName role
        ) {
}
