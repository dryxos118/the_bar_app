package com.example.the_bar_app.controllers;

import com.example.the_bar_app.interfaces.IAuthService;
import com.example.the_bar_app.models.dto.LoginDto;
import com.example.the_bar_app.models.dto.RegisterDto;
import com.example.the_bar_app.models.response.AuthResponse;
import com.example.the_bar_app.repository.UserRepository;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/auth")
@RestController
@Tag(name = "Auth")
public class AuthController {

    @Autowired
    private IAuthService authService;
    @Autowired
    private UserRepository userRepository;

    @PostMapping("/register")
    public ResponseEntity<?> register(@Valid @RequestBody RegisterDto registerDto) {
        if (userRepository.existsByUsername(registerDto.username())) {
            return ResponseEntity.badRequest().body("Username is already use");
        }
        if (userRepository.existsByEmail(registerDto.email())) {
            return ResponseEntity.badRequest().body("Email is already use");
        }

        try {
            AuthResponse authResponse = authService.register(registerDto);
            return ResponseEntity.ok(authResponse);
        } catch (Exception e) {
            return ResponseEntity.internalServerError().build();
        }
    }

    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(@Valid @RequestBody LoginDto loginDto) {
        try {
            AuthResponse authResponse = authService.login(loginDto);
            return ResponseEntity.ok(authResponse);
        } catch (Exception e) {
            return ResponseEntity.internalServerError().build();
        }
    }
}
