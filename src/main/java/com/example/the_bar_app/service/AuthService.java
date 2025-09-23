package com.example.the_bar_app.service;

import com.example.the_bar_app.service.impl.IAuthService;
import com.example.the_bar_app.entity.RoleName;
import com.example.the_bar_app.entity.User;
import com.example.the_bar_app.dto.LoginDto;
import com.example.the_bar_app.dto.RegisterDto;
import com.example.the_bar_app.dto.AuthResponse;
import com.example.the_bar_app.repository.UserRepository;
import com.example.the_bar_app.security.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthService implements IAuthService {

    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private JwtUtil jwtUtil;
    @Autowired
    private AuthenticationManager authManager;

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
                .password(passwordEncoder.encode(registerDto.password()))
                .role(role)
                .enabled(true)
                .build();

        userRepository.save(user);
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

        User user = userRepository.findByEmail(loginDto.usernameOrEmail())
                .or(() -> userRepository.findByUsername(loginDto.usernameOrEmail()))
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
