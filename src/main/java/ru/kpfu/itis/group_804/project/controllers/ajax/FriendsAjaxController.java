package ru.kpfu.itis.group_804.project.controllers.ajax;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ru.kpfu.itis.group_804.project.config.security.details.UserDetailsImpl;
import ru.kpfu.itis.group_804.project.dto.UserDto;
import ru.kpfu.itis.group_804.project.service.interfaces.FriendsService;

@RestController
public class FriendsAjaxController {

    @Autowired
    FriendsService friendsService;

    @PreAuthorize("isAuthenticated()")
    @PostMapping("/friends/breakFriendship")
    public int breakFriendship(@RequestParam("friend-id") Long friend, Authentication authentication) {
        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
        UserDto current = UserDto.from(userDetails.getUser());
        friendsService.breakFriendship(current.getId(), friend);
        return friendsService.getIncomingRequests(current.getId()).size();
    }

    @PreAuthorize("isAuthenticated()")
    @PostMapping("/friends/confirmFriendship")
    public void confirmFriendship(@RequestParam("sender-id") Long sender, Authentication authentication) {
        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
        UserDto current = UserDto.from(userDetails.getUser());
        friendsService.confirmFriend(current.getId(), sender);
    }

}
