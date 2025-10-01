package com.example.the_bar_app.dto.auth;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class AuthResponse {
    public String username;
    public String email;
    public String token;
}
