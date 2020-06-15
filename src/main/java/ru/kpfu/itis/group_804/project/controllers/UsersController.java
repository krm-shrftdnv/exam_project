package ru.kpfu.itis.group_804.project.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.kpfu.itis.group_804.project.config.security.details.UserDetailsImpl;
import ru.kpfu.itis.group_804.project.dto.UpdatedUserDto;
import ru.kpfu.itis.group_804.project.dto.UserDto;
import ru.kpfu.itis.group_804.project.service.interfaces.UsersService;

import java.util.List;

@Controller
public class UsersController {

    @Autowired
    private UsersService usersService;

    @PreAuthorize("hasAuthority('ADMIN')||hasAuthority('MODERATOR')")
    @GetMapping("/users")
    public String getUsersPage(Model model, Authentication authentication) {
        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
        UserDto current = UserDto.from(userDetails.getUser());
        model.addAttribute("current", current);
        List<UserDto> users = usersService.getAllUsers();
        model.addAttribute("users", users);
        return "users";
    }

    @PreAuthorize("hasAuthority('ADMIN')||hasAuthority('MODERATOR')")
    @PostMapping("/users/{user-id}/delete")
    public String deleteUser(@PathVariable("user-id") Long userId) {
        usersService.deleteUser(userId);
        return "redirect:/users";
    }

    @PreAuthorize("isAuthenticated()")
    @PostMapping("/users/updateUser")
    public String updateUser(UpdatedUserDto userDto, Authentication authentication){
        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
        UserDto current = UserDto.from(userDetails.getUser());
        usersService.updateUser(current.getId(), userDto);
        return "redirect:/profile";
    }

}
