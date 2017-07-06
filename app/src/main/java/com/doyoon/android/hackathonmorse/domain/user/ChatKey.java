package com.doyoon.android.hackathonmorse.domain.user;

/**
 * Created by DOYOON on 7/6/2017.
 */

public class ChatKey {

    private String imageUrl;
    private String friendName;
    private String friendRefKey;
    private String currentChatTime;
    private String chatRefKey;

    public ChatKey() {
    }

    public String getFriendName() {
        return friendName;
    }

    public void setFriendName(String friendName) {
        this.friendName = friendName;
    }

    public String getFriendRefKey() {
        return friendRefKey;
    }

    public void setFriendRefKey(String friendRefKey) {
        this.friendRefKey = friendRefKey;
    }

    public String getCurrentChatTime() {
        return currentChatTime;
    }

    public void setCurrentChatTime(String currentChatTime) {
        this.currentChatTime = currentChatTime;
    }

    public String getChatRefKey() {
        return chatRefKey;
    }

    public void setChatRefKey(String chatRefKey) {
        this.chatRefKey = chatRefKey;
    }
}
