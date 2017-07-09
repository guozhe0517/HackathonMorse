package com.doyoon.android.hackathonmorse.domain;

/**
 * Created by DOYOON on 7/6/2017.
 */

public class RemoteChatObj {


    private String lastChatDate;
    private String chats;

    public RemoteChatObj() {
    }

    public RemoteChatObj(String lastChatDate, String chats) {
        this.lastChatDate = lastChatDate;
        this.chats = chats;
    }

    public String getLastChatDate() {
        return lastChatDate;
    }

    public void setLastChatDate(String lastChatDate) {
        this.lastChatDate = lastChatDate;
    }

    public String getChats() {
        return chats;
    }

    public void setChats(String chats) {
        this.chats = chats;
    }
}
