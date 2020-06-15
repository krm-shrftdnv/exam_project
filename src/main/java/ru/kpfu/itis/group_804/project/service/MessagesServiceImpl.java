package ru.kpfu.itis.group_804.project.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import ru.kpfu.itis.group_804.project.dto.MessageDto;
import ru.kpfu.itis.group_804.project.dto.WrittenMessageDto;
import ru.kpfu.itis.group_804.project.models.Dialog;
import ru.kpfu.itis.group_804.project.models.Message;
import ru.kpfu.itis.group_804.project.models.User;
import ru.kpfu.itis.group_804.project.repository.DialogsRepository;
import ru.kpfu.itis.group_804.project.repository.MessageRepository;
import ru.kpfu.itis.group_804.project.repository.UsersRepository;
import ru.kpfu.itis.group_804.project.service.interfaces.MessagesService;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class MessagesServiceImpl implements MessagesService {
    @Autowired
    MessageRepository messageRepository;
    @Autowired
    DialogsRepository dialogsRepository;
    @Autowired
    UsersRepository usersRepository;

    @Transactional
    @Override
    public List<Message> getAllMessages(Long dialogId) {
        Dialog dialog = dialogsRepository.findById(dialogId).get();
        return messageRepository.findAllByDialogOrderByMessageDateTimeDesc(dialog);
    }

    @Override
    public List<Message> getLastNMessagesFromX(Long dialogId, int n, int x) {
        Page<Message> lastMessagesPage = messageRepository.findAll(PageRequest.of(x, n));
        return lastMessagesPage.getContent();
    }

    @Transactional
    @Override
    public void addMessage(WrittenMessageDto dto) {
        Dialog dialog = dialogsRepository.findById(dto.getDialogId()).get();
        User userFrom = usersRepository.findById(dto.getWriterId()).get();
        User userTo = usersRepository.findById(dialog.getMember1().getId().equals(dto.getWriterId()) ? dialog.getMember2().getId() : dialog.getMember1().getId()).get();
        Message message = Message.builder()
                .dialog(dialog)
                .message_text(dto.getText())
                .user_from(userFrom)
                .user_to(userTo)
                .messageDateTime(LocalDateTime.now())
                .build();
        messageRepository.save(message);
    }

    @Override
    public MessageDto getLastMessage(Long dialogId) {
        Dialog dialog = dialogsRepository.findById(dialogId).get();
        Message message = messageRepository.findTopByDialogOrderByMessageDateTime(dialog).get();
        return MessageDto.from(message);
    }

    @Override
    public void sendMessage(Long dialogId, String text, Long writerId, String writerNickname) {
        LocalDateTime date = LocalDateTime.now();
        WrittenMessageDto dto = WrittenMessageDto.builder()
                .dialogId(dialogId)
                .text(text)
                .writerId(writerId)
                .writerNickname(writerNickname)
                .date(date.toLocalDate() + " at " + date.getHour() + ":" + date.getMinute() + ":" + date.getSecond())
                .build();
        addMessage(dto);
    }

}
