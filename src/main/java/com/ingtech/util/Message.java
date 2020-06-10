package com.ingtech.util;

import java.util.Objects;

public class Message {

    private final String key;
    private final String message;

    public Message(String key, String message) {
        this.key = key;
        this.message = message;
    }


    public String getKey() {
        return key;
    }

    public String getMessage() {
        return message;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Message message1 = (Message) o;
        return Objects.equals(key, message1.key) &&
                message.equals(message1.message);
    }

    @Override
    public int hashCode() {
        return Objects.hash(key, message);
    }
}
