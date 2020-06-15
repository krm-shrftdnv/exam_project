package ru.kpfu.itis.group_804.project.controllers.ajax;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.kpfu.itis.group_804.project.config.security.details.UserDetailsImpl;
import ru.kpfu.itis.group_804.project.dto.UserDto;
import ru.kpfu.itis.group_804.project.service.interfaces.PostsService;

@RestController
public class PostsAjaxController {

    @Autowired
    PostsService postsService;

    @PreAuthorize("isAuthenticated()")
    @PostMapping("/post{post-id}/like")
    public int likePost(@PathVariable("post-id")Long postId, Authentication authentication){
        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
        UserDto current = UserDto.from(userDetails.getUser());
        postsService.likePost(postId, current.getId());
        return postsService.getLikers(postId).size();
    }

    @PreAuthorize("isAuthenticated()")
    @PostMapping("/post{post-id}/dislike")
    public int dislikePost(@PathVariable("post-id")Long postId, Authentication authentication){
        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
        UserDto current = UserDto.from(userDetails.getUser());
        postsService.dislikePost(postId, current.getId());
        return postsService.getLikers(postId).size();
    }

}
