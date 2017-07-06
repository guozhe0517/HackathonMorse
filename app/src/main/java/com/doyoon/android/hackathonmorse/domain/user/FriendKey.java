package com.doyoon.android.hackathonmorse.domain.user;

/**
 * Created by DOYOON on 7/6/2017.
 */

public class FriendKey {

    public String name;
    public String imageUrl;
    public String email;

    public String existChatRefKey;

    public FriendKey() {
    }

    public FriendKey(String name, String imageUrl, String email, String existChatRefKey) {
        this.name = name;
        this.imageUrl = imageUrl;
        this.email = email;
        this.existChatRefKey = existChatRefKey;
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

    @Override
    public String toString() {
        return "FriendKey{" +
                "name='" + name + '\'' +
                ", imageUrl='" + imageUrl + '\'' +
                ", email='" + email + '\'' +
                ", existChatRefKey='" + existChatRefKey + '\'' +
                '}';
    }
}
