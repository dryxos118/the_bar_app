package com.example.the_bar_app.service.impl;

import com.example.the_bar_app.dto.UserSummaryDto;
import com.example.the_bar_app.entity.RoleName;
import com.example.the_bar_app.entity.User;
import org.apache.coyote.BadRequestException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface IUserService {
    UserSummaryDto loadUserByUsername(String usernameOrEmail);
    Page<UserSummaryDto> listPageable(Pageable pageable);
    List<UserSummaryDto> listAll();
    UserSummaryDto changeRole(Long userId,RoleName roleName);
    boolean delete(Long userId);
    boolean enable(Long userId);
    UserSummaryDto userToDto(User user);
}
