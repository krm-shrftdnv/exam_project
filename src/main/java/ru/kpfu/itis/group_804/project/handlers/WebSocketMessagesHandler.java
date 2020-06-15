package ru.kpfu.itis.group_804.project.handlers;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.WebSocketMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.handler.TextWebSocketHandler;
import ru.kpfu.itis.group_804.project.dto.WrittenMessageDto;
import ru.kpfu.itis.group_804.project.service.interfaces.MessagesService;

import java.util.HashMap;
import java.util.Map;

@Component
@EnableWebSocket
public class WebSocketMessagesHandler extends TextWebSocketHandler {

    private static final Map<WebSocketSession, Long> sessions = new HashMap<>();

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private MessagesService messagesService;

    @Override
    public void handleMessage(WebSocketSession session, WebSocketMessage<?> message) throws Exception {
        if (message.toString().split(" ")[0].equals("TextMessage")) {
            String messageText = message.getPayload().toString();
            WrittenMessageDto messageDto = objectMapper.readValue(messageText, WrittenMessageDto.class);

            messagesService.addMessage(messageDto);

            if (!sessions.containsKey(session)) {
                sessions.put(session, messageDto.getDialogId());
            }

            for (WebSocketSession currentSession : sessions.keySet()) {
                if (sessions.get(currentSession).equals(messageDto.getDialogId())) {
                    currentSession.sendMessage(message);
                }
            }
        }
    }

}
