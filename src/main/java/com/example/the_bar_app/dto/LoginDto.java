package com.example.the_bar_app.dto;

import jakarta.validation.constraints.NotBlank;

public record LoginDto(
        @NotBlank String usernameOrEmail,
        @NotBlank String password) {
}
