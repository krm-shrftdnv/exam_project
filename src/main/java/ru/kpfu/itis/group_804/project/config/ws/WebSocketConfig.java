package ru.kpfu.itis.group_804.project.config.ws;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;
import ru.kpfu.itis.group_804.project.handlers.AuthHandshakeHandler;
import ru.kpfu.itis.group_804.project.handlers.WebSocketMessagesHandler;

@Configuration
public class WebSocketConfig implements WebSocketConfigurer {

    @Autowired
    private WebSocketMessagesHandler handler;
    @Autowired
    private AuthHandshakeHandler handshakeHandler;

    @Override
    public void registerWebSocketHandlers(WebSocketHandlerRegistry webSocketHandlerRegistry) {
        webSocketHandlerRegistry.addHandler(handler, "/websockets").setHandshakeHandler(handshakeHandler);
    }

}
