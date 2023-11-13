package com.example.wwmeet_backend.sse.controller;

import com.example.wwmeet_backend.sse.domain.SseConnectionPool;
import com.example.wwmeet_backend.sse.domain.UserSseConnection;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.util.Optional;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping(value = "/api/sse", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
public class SseController {
    private final SseConnectionPool<String, UserSseConnection> sseConnectionPool;

    @GetMapping("/connecct")
    public SseEmitter connect(@RequestParam String key) {
        log.info("connect");

        UserSseConnection userSseConnection = UserSseConnection.connect(key, sseConnectionPool);
        sseConnectionPool.addConnection(key, userSseConnection);

        return userSseConnection.getSseEmitter();
    }

//    @GetMapping("/push-event")
//    public void pushEvent(@RequestParam String id, @RequestParam String name) {
//        log.info("push");
//
//        UserSseConnection connection = sseConnectionPool.getConnection(id + name);
//        Optional.ofNullable(connection)
//                .ifPresent(it -> {
//                    it.sendMessage("test message");
//                });
//    }
}
