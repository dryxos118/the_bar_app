package com.example.the_bar_app.controller;

import com.example.the_bar_app.api.AppException;
import com.example.the_bar_app.api.ErrorType;
import com.example.the_bar_app.entity.User;
import com.example.the_bar_app.repository.UserRepository;
import com.example.the_bar_app.service.impl.IUserService;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@RestController
@RequestMapping(value = "/user")
@Tag(name = "User")
public class UserController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private IUserService userService;

    @GetMapping("/me")
    public ResponseEntity<?> me(Principal principal) {
        User user = userRepository.findByUsername(principal.getName())
                .or(() -> userRepository.findByEmail(principal.getName()))
                .orElseThrow();
        return ResponseEntity.ok(user);
    }

    @GetMapping("/page/list")
    public ResponseEntity<?> listPageable(
            @PageableDefault(size = 20) Pageable pageable) {
        return ResponseEntity.ok("");
    }

    @PatchMapping("/{id}/enabled")
    public boolean setEnabled(@PathVariable Long id, @RequestParam("value") boolean value) {
        return true;
    }

    @DeleteMapping("/{id}")
    public boolean delete(@PathVariable Long id) {
        return true;
    }
}
