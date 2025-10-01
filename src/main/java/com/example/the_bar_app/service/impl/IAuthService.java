package com.example.the_bar_app.service.impl;

import com.example.the_bar_app.dto.auth.LoginDto;
import com.example.the_bar_app.dto.auth.RegisterDto;
import com.example.the_bar_app.dto.auth.AuthResponse;

public interface IAuthService {
    AuthResponse register(RegisterDto registerDto);

    AuthResponse login(LoginDto loginDto);
}
