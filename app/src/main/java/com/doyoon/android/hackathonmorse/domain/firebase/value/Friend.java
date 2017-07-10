package com.doyoon.android.hackathonmorse.domain.firebase.value;

import com.doyoon.android.hackathonmorse.domain.firebase.FirebaseModel;

/**
 * Created by DOYOON on 7/6/2017.
 */

public class Friend extends FirebaseModel {

    private String key;
    private String uid;
    private String name;
    private String imageUrl;
    private String email;
    private String existChatKey;

    public Friend() {

    }

    public Friend(String key) {
        this.key = key;
    }

    public Friend(String uid, String name, String imageUrl, String email, String existChatKey) {
        this.name = name;
        this.imageUrl = imageUrl;
        this.email = email;
        this.existChatKey = existChatKey;
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

    public String getExistChatKey() {
        return existChatKey;
    }

    public void setExistChatKey(String existChatKey) {
        this.existChatKey = existChatKey;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    @Override
    public String toString() {
        return "Friend{" +
                "uid='" + uid + '\'' +
                ", name='" + name + '\'' +
                ", imageUrl='" + imageUrl + '\'' +
                ", email='" + email + '\'' +
                ", existChatKey='" + existChatKey + '\'' +
                '}';
    }

    @Override
    public String getKey() {
        return this.key;
    }

    @Override
    public void setKey(String key) {
        this.key = key;
    }

    public void fetchInfo(UserProfile friendProfile) {
        this.name = friendProfile.getName();
        this.name = friendProfile.getName();
        this.email = friendProfile.getEmail();
    }
}
