package com.doyoon.android.hackathonmorse.domain.firebase.value;

import com.doyoon.android.hackathonmorse.domain.firebase.FirebaseModel;

/**
 * Created by DOYOON on 7/6/2017.
 */

public class ChatKey extends FirebaseModel {

    private String chatRefKey;
    private String imageUrl;

    private String friendName;
    private String friendUid;

    private String currentChatTime;

    public ChatKey() {

    }

    public ChatKey(String chatRefKey, String imageUrl, String friendName, String friendUid, String currentChatTime) {
        this.chatRefKey = chatRefKey;
        this.imageUrl = imageUrl;
        this.friendName = friendName;
        this.friendUid = friendUid;
        this.currentChatTime = currentChatTime;
    }

    public String getFriendName() {
        return friendName;
    }

    public void setFriendName(String friendName) {
        this.friendName = friendName;
    }

    public String getFriendUid() {
        return friendUid;
    }

    public void setFriendUid(String friendUid) {
        this.friendUid = friendUid;
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


    @Override
    public String getValueKey() {
        return null;
    }

    @Override
    public String[] getReferenceKeys() {
        return new String[0];
    }

    @Override
    public String getDbPath() {
        return null;
    }
}
