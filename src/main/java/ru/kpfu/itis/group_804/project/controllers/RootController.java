package ru.kpfu.itis.group_804.project.controllers;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import ru.kpfu.itis.group_804.project.config.security.details.UserDetailsImpl;
import ru.kpfu.itis.group_804.project.dto.UserDto;

@PreAuthorize("isAuthenticated()")
@Controller
public class RootController {
    @GetMapping("/")
    public String getRootPage(Authentication authentication) {
        if (authentication != null) {
            UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
            UserDto user = UserDto.from(userDetails.getUser());
            return "redirect:/profile/id"+user.getId();
        } else return "redirect:/signIn";
    }
}
