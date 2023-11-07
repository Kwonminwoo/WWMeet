package com.example.wwmeet_backend.sse.domain;

import jakarta.annotation.security.DenyAll;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.io.IOException;


@Getter
public class UserSseConnection {
    private String key;
    private SseEmitter sseEmitter;
    private SseConnectionPool<String, UserSseConnection> sseConnectionPool;

    private UserSseConnection(String key, SseConnectionPool<String, UserSseConnection> sseConnectionPool) {
        this.key = key;
        this.sseConnectionPool = sseConnectionPool;

        this.sseEmitter = new SseEmitter(60 * 1000L);

        this.sseEmitter.onCompletion(() -> {
            this.sseConnectionPool.onCompletionCallback(this);
        });

        this.sseEmitter.onTimeout(() -> {
            this.sseEmitter.complete();
        });

        sendMessage("onopen", "connect");
    }

    public static UserSseConnection connect(String key, SseConnectionPool<String, UserSseConnection> sseConnectionPool){
        return new UserSseConnection(key, sseConnectionPool);
    }

    public void sendMessage(String eventName, Object data){
        try {
            this.sseEmitter.send(sseEmitter.event().name(eventName).data(data));
        } catch (IOException e) {
            this.sseEmitter.completeWithError(e);
        }
    }

    public void sendMessage(Object data){
        try {
            this.sseEmitter.send(sseEmitter.event().data(data));
        } catch (IOException e) {
            this.sseEmitter.completeWithError(e);
        }
    }
}
