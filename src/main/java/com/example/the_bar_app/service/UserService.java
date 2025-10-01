package com.example.the_bar_app.service;

import com.example.the_bar_app.api.AppException;
import com.example.the_bar_app.api.ErrorType;
import com.example.the_bar_app.dto.user.UserSummaryDto;
import com.example.the_bar_app.entity.user.RoleName;
import com.example.the_bar_app.entity.user.User;
import com.example.the_bar_app.repository.UserRepository;
import com.example.the_bar_app.service.impl.IUserService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService implements IUserService {

    private final UserRepository repo;

    @Override
    public UserSummaryDto loadUserByUsername(String usernameOrEmail) {
        User user = repo.findByEmail(usernameOrEmail)
                .or(() -> repo.findByUsername(usernameOrEmail))
                .orElseThrow(() -> new AppException(ErrorType.NOT_FOUND, "User not found"));
        return userToDto(user);
    }

    @Override
    public Page<UserSummaryDto> listPageable(Pageable pageable) {
        Page<User> page = repo.findAll(pageable);
        return page.map(this::userToDto);
    }

    @Override
    public List<UserSummaryDto> listAll() {
        List<User> users = repo.findAll();
        return users.stream().map(this::userToDto).toList();
    }

    @Override
    public UserSummaryDto changeRole(Long userId, RoleName roleName) {
        if (roleName == null) throw new IllegalArgumentException("Role must be null");
        User user = repo.findById(userId).orElseThrow(() -> notFound(userId));
        if (user.getRole() == RoleName.ADMIN && roleName != RoleName.ADMIN) {
            long admins = repo.countByRole(RoleName.ADMIN);
            if (admins <= 1) {
                throw new AppException(ErrorType.BAD_REQUEST, "Cannot downgrade the last ADMIN");
            }
        }
        user.setRole(roleName);
        repo.save(user);
        return userToDto(user);
    }

    @Override
    public boolean delete(Long userId) {
        User user = repo.findById(userId).orElseThrow(() -> notFound(userId));
        if (user.getRole() == RoleName.ADMIN && repo.countByRole(RoleName.ADMIN) <= 1) {
            throw new AppException(ErrorType.BAD_REQUEST, "Cannot delete the last ADMIN");
        }
        repo.delete(user);
        return true;
    }

    @Override
    public boolean enable(Long userId) {
        User user = repo.findById(userId).orElseThrow(() -> notFound(userId));
        user.setEnabled(!user.isEnabled());
        repo.save(user);
        return true;
    }

    @Override
    public UserSummaryDto userToDto(User user) {
        return new UserSummaryDto(user.getId(), user.getUsername(), user.getEmail(), user.getRole(), user.isEnabled());
    }

    private AppException notFound(Long id) {
        return new AppException(ErrorType.NOT_FOUND,"User " + id + " not found");
    }
}
