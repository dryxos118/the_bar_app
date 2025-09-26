package com.example.the_bar_app.controller;

import com.example.the_bar_app.entity.RoleName;
import com.example.the_bar_app.entity.User;
import com.example.the_bar_app.repository.UserRepository;
import com.example.the_bar_app.security.JwtAuthenticationFilter;
import com.example.the_bar_app.security.JwtUtil;
import jakarta.servlet.FilterChain;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(UserController.class)
@AutoConfigureMockMvc
@EnableMethodSecurity
public class UserControllerTest {

    @Autowired
    MockMvc mvc;

    @MockitoBean
    UserRepository userRepository;
    @MockitoBean
    JwtAuthenticationFilter jwtAuthenticationFilter;

    @BeforeEach
    void passThroughJwtFilter() throws Exception {
        doAnswer(inv -> {
            FilterChain chain = inv.getArgument(2);
            chain.doFilter(inv.getArgument(0), inv.getArgument(1));
            return null;
        }).when(jwtAuthenticationFilter).doFilter(any(), any(), any());
    }

    @Test
    @WithMockUser(username = "dryx", roles = {"USER"})
    void me_ok() throws Exception {
        User user = User.builder()
                .id(1L).username("dryx").email("dryx@thebar.app")
                .password("HASHED").role(RoleName.USER).enabled(true)
                .build();

        when(userRepository.findByUsername("dryx")).thenReturn(Optional.of(user));
        mvc.perform(get("/user/me")
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.username").value("dryx"));
    }

    @Test
    @WithMockUser(authorities = {"ADMIN"})
    void admin_all_ok_when_admin() throws Exception {
        when(userRepository.findAll()).thenReturn(java.util.List.of());
        mvc.perform(get("/user/admin/all"))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    @WithMockUser(authorities = {"USER"})
    void admin_all_forbidden_when_not_admin() throws Exception {
        mvc.perform(get("/user/admin/all"))
                .andDo(print())
                .andExpect(status().isForbidden());
    }
}
