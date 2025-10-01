package com.example.the_bar_app.service;

import com.example.the_bar_app.api.AppException;
import com.example.the_bar_app.api.ErrorType;
import com.example.the_bar_app.service.impl.IUserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CurrentUserService {
    private final IUserService service;

    public Long id() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth == null || !auth.isAuthenticated()) {
            throw new AppException(ErrorType.UNAUTHORIZED, "User not authenticated");
        }
        String usernameOrEmail = auth.getName();
        return service.loadUserByUsername(usernameOrEmail).id();
    }

    public String usernameOrEmail() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth == null || !auth.isAuthenticated()) {
            throw new AppException(ErrorType.UNAUTHORIZED, "User not authenticated");
        }
        return auth.getName();
    }
}
