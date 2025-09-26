package com.example.the_bar_app.controller;

import com.example.the_bar_app.dto.UserSummaryDto;
import com.example.the_bar_app.entity.RoleName;
import com.example.the_bar_app.service.impl.IUserService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping(value = "/user")
@Tag(name = "User")
@PreAuthorize("hasAuthority('ADMIN')")
@RequiredArgsConstructor
public class UserController {

    private final IUserService service;

    @GetMapping("/me")
    @PreAuthorize("permitAll()")
    public ResponseEntity<?> me(Principal principal) {
        UserSummaryDto user = service.loadUserByUsername(principal.getName());
        return ResponseEntity.ok(user);
    }

    @GetMapping("/list")
    public ResponseEntity<List<UserSummaryDto>> list() {
        List<UserSummaryDto> userSummaryDtoList = service.listAll();
        return ResponseEntity.ok(userSummaryDtoList);
    }

    @GetMapping("/page/list")
    public ResponseEntity<Page<UserSummaryDto>> listPageable(
            @RequestParam(defaultValue = "20") int count,
            @RequestParam(defaultValue = "0") int page
    ) {
        Pageable pageable = PageRequest.of(page, count, Sort.by("id").descending());
        Page<UserSummaryDto> userSummaryDtoPage = service.listPageable(pageable);
        return ResponseEntity.ok(userSummaryDtoPage);
    }

    @PatchMapping("/role/{id}")
    public ResponseEntity<UserSummaryDto> changeRole(@PathVariable Long id, @RequestParam RoleName role) {
        UserSummaryDto user = service.changeRole(id, role);
        return ResponseEntity.ok(user);
    }

    @PatchMapping("/enabled/{id}")
    public ResponseEntity<Boolean> setEnabled(@PathVariable Long id) {
        boolean success = service.enable(id);
        return ResponseEntity.ok(success);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Boolean> delete(@PathVariable Long id) {
        boolean success = service.delete(id);
        return ResponseEntity.ok(success);
    }
}
