package ru.kpfu.itis.group_804.project.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.kpfu.itis.group_804.project.dto.DialogDto;
import ru.kpfu.itis.group_804.project.models.Dialog;
import ru.kpfu.itis.group_804.project.models.User;
import ru.kpfu.itis.group_804.project.repository.DialogsRepository;
import ru.kpfu.itis.group_804.project.repository.UsersRepository;
import ru.kpfu.itis.group_804.project.service.interfaces.DialogService;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
public class DialogServiceImpl implements DialogService {
    @Autowired
    DialogsRepository dialogsRepository;
    @Autowired
    UsersRepository usersRepository;

    @Transactional
    @Override
    public List<DialogDto> getAllDialogs(Long id) {
        User user = usersRepository.findById(id).get();
        List<Dialog> dialogs = new ArrayList<>(user.getDialogsWhereIsMember1());
        dialogs.addAll(user.getDialogsWhereIsMember2());
        return DialogDto.from(dialogs);
    }

    @Transactional
    @Override
    public Long createDialog(Long writer_id, Long goal_id) {
        User writer = usersRepository.findById(writer_id).get();
        User goal = usersRepository.findById(goal_id).get();
        Dialog dialog = Dialog.builder()
                .member1(writer)
                .member2(goal)
                .dialog_datetime(LocalDateTime.now())
                .build();
        dialogsRepository.save(dialog);

        Dialog createdDialog = dialogsRepository.findByMember1AndMember2(writer, goal).get();
        return createdDialog.getId();
    }

    @Transactional
    @Override
    public Optional<Dialog> getDialog(Long currentId, Long friendId) {
        User current = usersRepository.findById(currentId).get();
        User friend = usersRepository.findById(friendId).get();
        if (dialogsRepository.findByMember1AndMember2(current, friend).isPresent()) {
            return dialogsRepository.findByMember1AndMember2(current, friend);
        } else if (dialogsRepository.findByMember1AndMember2(friend, current).isPresent()) {
            return dialogsRepository.findByMember1AndMember2(friend, current);
        }
        return Optional.empty();
    }

    @Transactional
    @Override
    public Optional<Dialog> getDialogById(Long dialogId) {
        return dialogsRepository.findById(dialogId);
    }

}
