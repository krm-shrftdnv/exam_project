package ru.kpfu.itis.group_804.project.controllers.ajax;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ru.kpfu.itis.group_804.project.models.enums.Role;
import ru.kpfu.itis.group_804.project.service.interfaces.UsersService;

@RestController
public class UsersAjaxController {

    @Autowired
    UsersService usersService;

    @PreAuthorize("hasAuthority('ADMIN')||hasAuthority('MODERATOR')")
    @GetMapping("/users/confirm")
    public void confirmUser(@RequestParam("user-id") Long userId) {
        usersService.confirmUser(userId);
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @GetMapping("/users/changeRole")
    public void changeUserRole(@RequestParam("user-id") String userId, @RequestParam("role")String role) {
        usersService.changeUserRole(Long.valueOf(userId), Role.valueOf(role));
    }

}
