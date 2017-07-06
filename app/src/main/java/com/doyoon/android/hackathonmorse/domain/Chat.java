package com.doyoon.android.hackathonmorse.domain;

/**
 * Created by DOYOON on 7/6/2017.
 */

public class Chat {


    private String owner;
    private String message;

    public Chat() {

    }

    public Chat(String owner, String message) {
        this.owner = owner;
        this.message = message;
    }

    public String getOwner() {
        return owner;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    @Override
    public String toString() {
        return "Chat{" +
                "owner='" + owner + '\'' +
                ", message='" + message + '\'' +
                '}';
    }
}
