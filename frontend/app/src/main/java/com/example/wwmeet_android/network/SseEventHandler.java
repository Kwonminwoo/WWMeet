package com.example.wwmeet_android.network;

import android.util.Log;

import com.launchdarkly.eventsource.MessageEvent;
import com.launchdarkly.eventsource.background.BackgroundEventHandler;

public class SseEventHandler implements BackgroundEventHandler {

    public SseEventHandler() {
    }

    @Override
    public void onOpen() throws Exception {
        Log.e("", "open success");
    }

    @Override
    public void onClosed() throws Exception {
        Log.e("", "close success");
    }

    @Override
    public void onMessage(String event, MessageEvent messageEvent) throws Exception {
        Log.e("event", event);
        Log.e("message", messageEvent.getData());
    }

    @Override
    public void onComment(String comment) throws Exception {

    }

    @Override
    public void onError(Throwable t) {
        Log.e("sse error", t.getMessage());
    }
}
