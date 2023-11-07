package com.example.wwmeet_backend.sse.domain;

public interface SseConnectionPool<T, R> {
    void addConnection(T key, R connection);
    R getConnection(T key);

    void onCompletionCallback(R connection);
}
