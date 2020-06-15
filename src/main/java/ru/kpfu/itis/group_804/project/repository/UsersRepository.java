package ru.kpfu.itis.group_804.project.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.kpfu.itis.group_804.project.models.FriendshipRequest;
import ru.kpfu.itis.group_804.project.models.User;

import java.util.List;
import java.util.Optional;

public interface UsersRepository extends JpaRepository<User, Long> {
    Optional<User>findUserByEmail(String email);
    Optional<User>findUserByNickname(String nickname);
}
