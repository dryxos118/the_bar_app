package com.example.the_bar_app.service;

import com.example.the_bar_app.api.AppException;
import com.example.the_bar_app.api.ErrorType;
import com.example.the_bar_app.dto.UserSummaryDto;
import com.example.the_bar_app.entity.RoleName;
import com.example.the_bar_app.entity.User;
import com.example.the_bar_app.repository.UserRepository;
import com.example.the_bar_app.service.impl.IUserService;
import org.apache.coyote.BadRequestException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService implements IUserService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public Page<UserSummaryDto> listPageable(Pageable pageable) {
        Page<User> page = userRepository.findAll(pageable);
        return page.map(this::userToDto);
    }

    @Override
    public List<UserSummaryDto> listAll() {
        List<User> users = userRepository.findAll();
        return users.stream().map(this::userToDto).toList();
    }

    @Override
    public UserSummaryDto changeRole(Long userId, RoleName roleName) throws AppException {
        if (roleName == null) throw new IllegalArgumentException("Role must be null");
        User user = userRepository.findById(userId).orElseThrow(() -> new AppException(ErrorType.BAD_REQUEST, "User not found"));
        if (user.getRole() == RoleName.ADMIN && roleName != RoleName.ADMIN) {
            long admins = userRepository.countByRole(RoleName.ADMIN);
            if (admins <= 1) {
                throw new AppException(ErrorType.BAD_REQUEST, "Impossible it's a last ADMIN");
            }
        }
        user.setRole(roleName);
        userRepository.save(user);
        return userToDto(user);
    }

    @Override
    public boolean delete(Long userId) throws AppException {
        User user = userRepository.findById(userId).orElseThrow(() -> new AppException(ErrorType.BAD_REQUEST, "User not found"));
        if (user.getRole() == RoleName.ADMIN && userRepository.countByRole(RoleName.ADMIN) <= 1) {
            throw new AppException(ErrorType.BAD_REQUEST, "Impossible it's a last ADMIN");
        }
        userRepository.delete(user);
        return true;
    }

    @Override
    public boolean enable(Long userId) throws AppException {
        User user = userRepository.findById(userId).orElseThrow(() -> new AppException(ErrorType.BAD_REQUEST, "User not found"));
        user.setEnabled(!user.isEnabled());
        userRepository.save(user);
        return true;
    }

    @Override
    public UserSummaryDto userToDto(User user) {
        return new UserSummaryDto(user.getId(), user.getUsername(), user.getEmail(), user.getRole(), user.isEnabled());
    }
}
