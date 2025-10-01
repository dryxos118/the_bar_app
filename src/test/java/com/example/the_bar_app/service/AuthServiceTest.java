package com.example.the_bar_app.service;

import com.example.the_bar_app.dto.auth.AuthResponse;
import com.example.the_bar_app.dto.auth.RegisterDto;
import com.example.the_bar_app.entity.user.RoleName;
import com.example.the_bar_app.entity.user.User;
import com.example.the_bar_app.repository.UserRepository;
import com.example.the_bar_app.security.JwtUtil;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.password.PasswordEncoder;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;


public class AuthServiceTest {

    @Mock
    private PasswordEncoder passwordEncoder;
    @Mock
    private UserRepository userRepository;
    @Mock
    private JwtUtil jwtUtil;
    @Mock
    private AuthenticationManager authManager;

    @InjectMocks
    private AuthService authService;

    @BeforeEach
    void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void register_success() {
        RegisterDto dto = new RegisterDto("dryx", "dryx@thebar.app", "dryx1234", RoleName.ADMIN);

        // Arrange
        when(passwordEncoder.encode("dryx1234")).thenReturn("ENCODED_PWD");
        when(jwtUtil.generateToken(any(), eq(RoleName.ADMIN))).thenReturn("FAKE_JWT");

        // Act
        AuthResponse response = authService.register(dto);

        // Assert
        ArgumentCaptor<User> userCaptor = ArgumentCaptor.forClass(User.class);
        verify(userRepository).save(userCaptor.capture());
        User savedUser = userCaptor.getValue();

        assertThat(savedUser.getUsername()).isEqualTo("dryx");
        assertThat(savedUser.getEmail()).isEqualTo("dryx@thebar.app");
        assertThat(savedUser.getPassword()).isEqualTo("ENCODED_PWD");
        assertThat(savedUser.getRole()).isEqualTo(RoleName.ADMIN);

        assertThat(response.username).isEqualTo("dryx");
        assertThat(response.email).isEqualTo("dryx@thebar.app");
        assertThat(response.token).isEqualTo("FAKE_JWT");
    }

    @Test
    void register_success_whenRoleIsNull() {
        RegisterDto dto = new RegisterDto("dryx", "dryx@thebar.app", "dryx1234", null);

        // Arrange
        when(passwordEncoder.encode("dryx1234")).thenReturn("ENCODED_PWD");
        when(jwtUtil.generateToken(any(),eq(RoleName.USER))).thenReturn("FAKE_JWT");
        // Act
        AuthResponse response = authService.register(dto);
        // Assert
        ArgumentCaptor<User> userCaptor = ArgumentCaptor.forClass(User.class);
        verify(userRepository).save(userCaptor.capture());
        User userSaved = userCaptor.getValue();
        assertThat(userSaved.getRole()).isEqualTo(RoleName.USER);
        assertThat(response.token).isEqualTo("FAKE_JWT");
    }
}
