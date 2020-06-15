package ru.kpfu.itis.group_804.project.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import ru.kpfu.itis.group_804.project.models.Dialog;
import ru.kpfu.itis.group_804.project.models.Message;
import ru.kpfu.itis.group_804.project.utils.DialogMessageID;

import java.util.List;
import java.util.Optional;

public interface MessageRepository extends JpaRepository<Message, DialogMessageID> {
    List<Message> findAllByDialogOrderByMessageDateTimeDesc(Dialog dialog);
    Page<Message>  findAll(Pageable pageable);
    Optional<Message> findTopByDialogOrderByMessageDateTime(Dialog dialog);
}
