package com.doyoon.android.hackathonmorse.domain.firebase.value;

import com.doyoon.android.hackathonmorse.domain.firebase.FirebaseModel;

/**
 * Created by DOYOON on 7/6/2017.
 */

public class UserChatroom extends FirebaseModel{

    private String key;
    private String frinedKey;

    private String chatRefKey;
    private String imageUrl;

    private String friendName;
    private String friendUid;

    private String currentChatTime;

    public UserChatroom() {

    }

    public UserChatroom(String chatRefKey, String imageUrl, String friendName, String friendUid, String currentChatTime) {
        this.chatRefKey = chatRefKey;
        this.imageUrl = imageUrl;
        this.friendName = friendName;
        this.friendUid = friendUid;
        this.currentChatTime = currentChatTime;
    }


    @Override
    public String getKey() {
        return this.key;
    }

    @Override
    public void setKey(String key) {
        this.key = key;
    }

    public String getChatRefKey() {
        return chatRefKey;
    }

    public void setChatRefKey(String chatRefKey) {
        this.chatRefKey = chatRefKey;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
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

    public String getFrinedKey() {
        return frinedKey;
    }

    public void setFrinedKey(String frinedKey) {
        this.frinedKey = frinedKey;
    }
}
