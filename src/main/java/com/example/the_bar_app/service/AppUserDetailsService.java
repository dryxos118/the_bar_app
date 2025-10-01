package com.example.the_bar_app.service;

import java.util.Set;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.example.the_bar_app.entity.user.User;
import com.example.the_bar_app.repository.UserRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AppUserDetailsService implements UserDetailsService {
    private final UserRepository repo;

    @Override
    public UserDetails loadUserByUsername(String usernameOrEmail) throws UsernameNotFoundException {
        User user = repo.findByUsername(usernameOrEmail)
            .or(() -> repo.findByEmail(usernameOrEmail))
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
