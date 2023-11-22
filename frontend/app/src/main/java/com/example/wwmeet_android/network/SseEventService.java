package com.example.wwmeet_android.network;

import android.media.metrics.Event;

//import com.launchdarkly.eventsource.EventSource;
//import com.launchdarkly.eventsource.MessageEvent;
//import com.launchdarkly.eventsource.background.BackgroundEventHandler;
//import com.launchdarkly.eventsource.ConnectStrategy;
//import com.launchdarkly.eventsource.background.BackgroundEventSource;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;

public class SseEventService {
    private SseEventHandler sseEventHandler;
    public SseEventService() {
    }

    public void startSse(String key) throws URISyntaxException {
       /* BackgroundEventSource bes = new BackgroundEventSource.Builder(new SseEventHandler(),
                new EventSource.Builder(
                        ConnectStrategy.http(new URI("https//localhost:8080/connect?key=" + key))
                                .connectTimeout(5, TimeUnit.SECONDS)
                )
        )
                .threadPriority(Thread.MAX_PRIORITY)
                .build();

        bes.start();*/
    }

    public SseEventHandler getSseEventHandler() {
        return sseEventHandler;
    }

    public void setSseEventHandler(SseEventHandler sseEventHandler) {
        this.sseEventHandler = sseEventHandler;
    }
}
