package com.tableorder.server.handler;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import java.io.IOException;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Slf4j
@Component
@RequiredArgsConstructor
public class WebSocketHandler extends TextWebSocketHandler {

    private final ObjectMapper objectMapper; // JSON <-> Java 객체 변환기

    private final Map<String, WebSocketSession> sessions = new ConcurrentHashMap<>();

    // 클라이언트가 처음 연결했을 때 호출되는 메소드
    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        sessions.put(session.getId(), session); // 연결된 클라이언트를 리스트에 추가
        log.info("새로운 클라이언트 연결: {}, 현재 연결된 클라이언트 수: {}", session.getId(), sessions.size());
    }

    // 클라이언트로부터 메시지를 받았을 때 호출되는 메소드 (지금은 사용하지 않음)
    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
        // 이 프로젝트에서는 서버가 일방적으로 알림만 보내므로, 이 부분은 비워둡니다.
        log.info("메시지 수신: {} 로부터: {}", session.getId(), message.getPayload());
    }

    // 클라이언트 연결이 끊겼을 때 호출되는 메소드
    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
        sessions.remove(session.getId()); // 연결이 끊긴 클라이언트를 리스트에서 제거
        log.info("클라이언트 연결 끊김: {}, 현재 연결된 클라이언트 수: {}", session.getId(), sessions.size());
    }

    // [중요] 모든 연결된 클라이언트에게 메시지를 보내는 기능
    public void broadcast(Object data) throws IOException {
        String jsonMessage = objectMapper.writeValueAsString(data);
        TextMessage textMessage = new TextMessage(jsonMessage);

        log.info("📢 [WebSocket] 전체 방송 시작! 대상: {}명, 내용: {}", sessions.size(), jsonMessage);

        for (WebSocketSession session : sessions.values()) {
            if (session.isOpen()) {
                session.sendMessage(textMessage);
                log.info("메시지 발송 완료: {} 에게: {}", session.getId(), jsonMessage);
            }
        }
    }
}