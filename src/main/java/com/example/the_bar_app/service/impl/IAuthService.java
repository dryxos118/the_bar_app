package com.example.the_bar_app.service.impl;

import com.example.the_bar_app.dto.LoginDto;
import com.example.the_bar_app.dto.RegisterDto;
import com.example.the_bar_app.dto.AuthResponse;

public interface IAuthService {
    AuthResponse register(RegisterDto registerDto);

    AuthResponse login(LoginDto loginDto);
}
