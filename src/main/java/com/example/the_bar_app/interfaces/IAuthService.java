package com.example.the_bar_app.interfaces;

import com.example.the_bar_app.models.dto.LoginDto;
import com.example.the_bar_app.models.dto.RegisterDto;
import com.example.the_bar_app.models.response.AuthResponse;

public interface IAuthService {
    AuthResponse register(RegisterDto registerDto);

    AuthResponse login(LoginDto loginDto);
}
