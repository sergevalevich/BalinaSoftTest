package com.valevich.balinasofttest.eventbus.events;

import java.util.Map;

public class ContactSelectedEvent {
    private Map.Entry<String,String> mContact;

    public ContactSelectedEvent(Map.Entry<String, String> contact) {
        mContact = contact;
    }

    public Map.Entry<String, String> getContact() {
        return mContact;
    }
}
