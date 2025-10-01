package com.example.the_bar_app.controller;

import com.example.the_bar_app.dto.auth.AuthResponse;
import com.example.the_bar_app.dto.auth.RegisterDto;
import com.example.the_bar_app.entity.user.RoleName;
import com.example.the_bar_app.repository.UserRepository;
import com.example.the_bar_app.security.JwtAuthenticationFilter;
import com.example.the_bar_app.security.JwtUtil;
import com.example.the_bar_app.service.impl.IAuthService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(AuthController.class)
@AutoConfigureMockMvc(addFilters = false)
public class AuthControllerTest {

    @Autowired
    MockMvc mvc;
    @Autowired
    ObjectMapper om;

    @MockitoBean
    IAuthService authService;
    @MockitoBean
    UserRepository userRepository;
    @MockitoBean
    JwtAuthenticationFilter jwtAuthenticationFilter;
    @MockitoBean
    JwtUtil jwtUtil;

    @Test
    void register_success() throws Exception {
        RegisterDto dto = new RegisterDto("dryx", "dryx@thebar.app", "dryx1234", RoleName.ADMIN);
        // Arrange
        when(userRepository.existsByUsername("dryx")).thenReturn(false);
        when(userRepository.existsByEmail("dryx@thebar.app")).thenReturn(false);
        when(authService.register(eq(dto)))
                .thenReturn(new AuthResponse("dryx", "dryx@thebar.app", "FAKE_JWT"));
        // Act & Assert
        mvc.perform(post("/auth/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(om.writeValueAsString(dto)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.username").value("dryx"))
                .andExpect(jsonPath("$.email").value("dryx@thebar.app"))
                .andExpect(jsonPath("$.token").value("FAKE_JWT"));
    }
}
