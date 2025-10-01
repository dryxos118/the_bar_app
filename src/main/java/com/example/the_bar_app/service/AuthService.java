package com.example.the_bar_app.service;

import com.example.the_bar_app.service.impl.IAuthService;
import com.example.the_bar_app.entity.user.RoleName;
import com.example.the_bar_app.entity.user.User;
import com.example.the_bar_app.dto.auth.LoginDto;
import com.example.the_bar_app.dto.auth.RegisterDto;
import com.example.the_bar_app.dto.auth.AuthResponse;
import com.example.the_bar_app.repository.UserRepository;
import com.example.the_bar_app.security.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService implements IAuthService {

    private final PasswordEncoder encoder;
    private final UserRepository repo;
    private final JwtUtil jwtUtil;
    private final AuthenticationManager authManager;

    @Override
    public AuthResponse register(RegisterDto registerDto) {
        RoleName role;
        if (registerDto.role() == null) {
            role = RoleName.USER;
        } else {
            role = registerDto.role();
        }
        User user = User.builder()
                .username(registerDto.username())
                .email(registerDto.email())
                .password(encoder.encode(registerDto.password()))
                .role(role)
                .enabled(true)
                .build();

        repo.save(user);
        String token = jwtUtil.generateToken(
                org.springframework.security.core.userdetails.User
                        .withUsername(user.getUsername())
                        .password(user.getPassword())
                        .authorities(user.getRole().name())
                        .build(),
                user.getRole());

        return new AuthResponse(user.getUsername(), user.getEmail(), token);
    }

    @Override
    public AuthResponse login(LoginDto loginDto) {
        authManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginDto.usernameOrEmail(), loginDto.password())
        );

        User user = repo.findByEmail(loginDto.usernameOrEmail())
                .or(() -> repo.findByUsername(loginDto.usernameOrEmail()))
                .orElseThrow();
        String token = jwtUtil.generateToken(
                org.springframework.security.core.userdetails.User
                        .withUsername(user.getUsername())
                        .password(user.getPassword())
                        .authorities(user.getRole().name())
                        .build(), user.getRole());

        return new AuthResponse(user.getUsername(), user.getEmail(), token);
    }
}
