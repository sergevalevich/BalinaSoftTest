package com.valevich.balinasofttest.eventbus.events;

public class ErrorEvent {
    private String mMessage;

    public ErrorEvent(String message) {
        mMessage = message;
    }

    public String getMessage() {
        return mMessage;
    }
}
