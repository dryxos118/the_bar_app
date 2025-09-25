package com.example.the_bar_app.service.impl;

import com.example.the_bar_app.dto.UserSummaryDto;
import com.example.the_bar_app.entity.RoleName;
import com.example.the_bar_app.entity.User;
import org.apache.coyote.BadRequestException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface IUserService {
    Page<UserSummaryDto> listPageable(Pageable pageable);
    List<UserSummaryDto> listAll();
    UserSummaryDto changeRole(Long userId,RoleName roleName) throws BadRequestException;
    boolean delete(Long userId) throws BadRequestException;
    boolean enable(Long userId) throws BadRequestException;
    UserSummaryDto userToDto(User user);
}
