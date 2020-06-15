package ru.kpfu.itis.group_804.project.service.interfaces;

import ru.kpfu.itis.group_804.project.dto.MessageDto;
import ru.kpfu.itis.group_804.project.dto.WrittenMessageDto;
import ru.kpfu.itis.group_804.project.models.Message;

import java.util.List;

public interface MessagesService {

    List<Message> getLastNMessagesFromX(Long dialogId, int n, int x);
    List<Message> getAllMessages(Long dialogId);
    void addMessage(WrittenMessageDto dto);
    MessageDto getLastMessage(Long dialogId);
    void sendMessage(Long dialogId, String text, Long writerId, String writerNickname);
}
