package com.example.the_bar_app.models.response;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class AuthResponse {
    public String username;
    public String email;
    public String token;
}
