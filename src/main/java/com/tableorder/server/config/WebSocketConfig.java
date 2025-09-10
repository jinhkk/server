package com.tableorder.server.config;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;

@Configuration
@EnableWebSocket // WebSocket 기능 사용할거라는 의미
@RequiredArgsConstructor
public class WebSocketConfig implements WebSocketConfigurer {
    private final com.tableorder.server.handler.WebSocketHandler webSocketHandler;


    @Override
    public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
        registry.addHandler(webSocketHandler, "/ws/order")
                .setAllowedOrigins("*");  // /ws/order 라는 주소로 접속 시도하면 핸들러에게 연결 요청함
    }
}
