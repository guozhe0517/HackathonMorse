package com.doyoon.android.hackathonmorse.domain;

/**
 * Created by DOYOON on 7/6/2017.
 */

public class User {

    private String jsonChatKeyList;
    private String jsonFriendKeyList;

    public User() {
    }

    public User(String jsonChatKeyList, String jsonFriendKeyList) {
        this.jsonChatKeyList = jsonChatKeyList;
        this.jsonFriendKeyList = jsonFriendKeyList;
    }

    public String getJsonChatKeyList() {
        return jsonChatKeyList;
    }

    public void setJsonChatKeyList(String jsonChatKeyList) {
        this.jsonChatKeyList = jsonChatKeyList;
    }

    public String getJsonFriendKeyList() {
        return jsonFriendKeyList;
    }

    public void setJsonFriendKeyList(String jsonFriendKeyList) {
        this.jsonFriendKeyList = jsonFriendKeyList;
    }
}
