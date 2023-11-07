package com.example.wwmeet_backend.sse.domain;

import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Component
public class DefaultSseConnectionPool implements SseConnectionPool<String, UserSseConnection>{
    private static final Map<String, UserSseConnection> connectionPool = new ConcurrentHashMap<>();

    @Override
    public void addConnection(String key, UserSseConnection connection) {
        connectionPool.put(key, connection);
    }

    @Override
    public UserSseConnection getConnection(String key) {
        return connectionPool.get(key);
    }

    @Override
    public void onCompletionCallback(UserSseConnection connection) {
        connectionPool.remove(connection.getKey());
    }
}
