package ru.kpfu.itis.group_804.project.service.interfaces;

import ru.kpfu.itis.group_804.project.dto.UpdatedUserDto;
import ru.kpfu.itis.group_804.project.dto.UserDto;
import ru.kpfu.itis.group_804.project.models.User;
import ru.kpfu.itis.group_804.project.models.enums.Role;

import java.util.List;

public interface UsersService {
    List<UserDto> getAllUsers();

    void deleteUser(Long userId);

    void confirmUser(Long userId);

    void changeUserRole(Long userId, Role role);

    User getUserById(Long userId);

    boolean userExists(Long id);

    void updateUser(Long userId, UpdatedUserDto dto);
}
