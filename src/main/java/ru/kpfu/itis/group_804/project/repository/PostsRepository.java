package ru.kpfu.itis.group_804.project.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.kpfu.itis.group_804.project.models.Post;
import ru.kpfu.itis.group_804.project.models.User;

import java.util.List;

public interface PostsRepository extends JpaRepository<Post, Long> {
    List<Post> findAllByWriter(User writer);
}
