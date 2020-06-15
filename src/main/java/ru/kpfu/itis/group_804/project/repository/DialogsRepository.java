package ru.kpfu.itis.group_804.project.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.kpfu.itis.group_804.project.dto.DialogDto;
import ru.kpfu.itis.group_804.project.models.Dialog;
import ru.kpfu.itis.group_804.project.models.User;

import java.util.List;
import java.util.Optional;

public interface DialogsRepository extends JpaRepository<Dialog, Long> {
    List<Dialog> findAllByMember1(User user);
    List<Dialog> findAllByMember2(User user);
    Optional<Dialog> findByMember1AndMember2(User member1, User member2);
}
