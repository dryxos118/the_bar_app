package com.example.the_bar_app.service;

import java.util.Set;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.example.the_bar_app.entity.User;
import com.example.the_bar_app.repository.UserRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {
    private final UserRepository users;

    @Override
    public UserDetails loadUserByUsername(String usernameOrEmail) throws UsernameNotFoundException {
        User user = users.findByUsername(usernameOrEmail)
            .or(() -> users.findByEmail(usernameOrEmail))
            .orElseThrow(() -> new UsernameNotFoundException("User not found"));

        Set<GrantedAuthority> auths = Set.of(new SimpleGrantedAuthority(user.getRole().name()));

        return org.springframework.security.core.userdetails.User
            .withUsername(user.getUsername())
            .password(user.getPassword())
            .authorities(auths)
            .accountLocked(!user.isEnabled())
            .disabled(!user.isEnabled())
            .build();
    }
}
