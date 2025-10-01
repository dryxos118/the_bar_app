package com.example.the_bar_app.controller;

import com.example.the_bar_app.api.AppException;
import com.example.the_bar_app.api.ErrorType;
import com.example.the_bar_app.service.impl.IAuthService;
import com.example.the_bar_app.dto.auth.LoginDto;
import com.example.the_bar_app.dto.auth.RegisterDto;
import com.example.the_bar_app.dto.auth.AuthResponse;
import com.example.the_bar_app.repository.UserRepository;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/auth")
@RestController
@Tag(name = "Auth")
@RequiredArgsConstructor
public class AuthController {

    private final IAuthService service;
    private final UserRepository repo;

    @PostMapping("/register")
    public ResponseEntity<?> register(@Valid @RequestBody RegisterDto registerDto) {
        if (repo.existsByUsername(registerDto.username())) {
            throw new AppException(ErrorType.BAD_REQUEST, "Username is already use");
        }
        if (repo.existsByEmail(registerDto.email())) {
            throw new AppException(ErrorType.BAD_REQUEST, "Email is already use");
        }
        try {
            AuthResponse authResponse = service.register(registerDto);
            return ResponseEntity.ok(authResponse);
        } catch (Exception e) {
            throw new AppException(ErrorType.INTERNAL_SERVER_ERROR, "");
        }
    }

    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(@Valid @RequestBody LoginDto loginDto) {
        try {
            AuthResponse authResponse = service.login(loginDto);
            return ResponseEntity.ok(authResponse);
        } catch (Exception e) {
            throw new AppException(ErrorType.INTERNAL_SERVER_ERROR, e.getMessage());
        }
    }
}
