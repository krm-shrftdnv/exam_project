package ru.kpfu.itis.group_804.project.service.interfaces;

import ru.kpfu.itis.group_804.project.dto.WrittenPostDto;
import ru.kpfu.itis.group_804.project.models.Post;
import ru.kpfu.itis.group_804.project.models.User;

import java.util.List;
import java.util.Optional;

public interface PostsService {
    List<Post> getAllPosts(Long userId);
    void addNewPost(Long writerId, WrittenPostDto form);
    void likePost(Long postId, Long likerId);
    void dislikePost(Long postId, Long likerId);
    List<User> getLikers(Long postId);
    boolean userLikesPost(Long userId, Long postId);
    Optional<Post> getPostById(Long postId);
}
