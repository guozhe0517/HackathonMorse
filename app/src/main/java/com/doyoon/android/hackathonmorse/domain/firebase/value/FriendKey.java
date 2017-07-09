package com.doyoon.android.hackathonmorse.domain.firebase.value;

import com.doyoon.android.hackathonmorse.domain.firebase.FirebaseModel;

/**
 * Created by DOYOON on 7/6/2017.
 */

public class FriendKey extends FirebaseModel {

    public String uid;
    public String name;
    public String imageUrl;
    public String email;

    public String existChatRefKey;

    public FriendKey() {

    }

    public FriendKey(String uid, String name, String imageUrl, String email, String existChatRefKey) {
        this.name = name;
        this.imageUrl = imageUrl;
        this.email = email;
        this.existChatRefKey = existChatRefKey;
        this.uid = uid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getExistChatRefKey() {
        return existChatRefKey;
    }

    public void setExistChatRefKey(String existChatRefKey) {
        this.existChatRefKey = existChatRefKey;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    @Override
    public String toString() {
        return "FriendKey{" +
                "uid='" + uid + '\'' +
                ", name='" + name + '\'' +
                ", imageUrl='" + imageUrl + '\'' +
                ", email='" + email + '\'' +
                ", existChatRefKey='" + existChatRefKey + '\'' +
                '}';
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
